package com.xu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.bean.Authority;
import com.xu.bean.Role;
import com.xu.bean.UserDetail;
import com.xu.bean.custom.UserAuthorityBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.xu.common.Constant.CERT_SERVICE;

@Service
public class SteamUserDetailServiceImpl implements UserDetailsService {


    private static final Logger logger = LoggerFactory.getLogger(SteamUserDetailServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail param = new UserDetail();
        param.setPhone(username);
        UserAuthorityBo userAuthorityBo = null;
        try {
            ResponseEntity<UserAuthorityBo> responseEntity = restTemplate.postForEntity(CERT_SERVICE+"/users/authority",param,UserAuthorityBo.class);
            userAuthorityBo=responseEntity.getBody();
        }catch (Exception e){
            logger.error(e.toString());
        }
        String password="root";
        StringBuilder authorityStr =new StringBuilder( );
        if(userAuthorityBo!=null){
            password = userAuthorityBo.getUserDetail().getUserPassword();
            List<Authority> authorityList = userAuthorityBo.getAuthorityList();
            List<Role> roleList = userAuthorityBo.getRoleList();
            for (int i=0;i<authorityList.size();i++){
                Authority item =authorityList.get(i);
                if(i!=0)authorityStr.append(",");
                authorityStr.append(item.getAuthorityCode());
            }

            for (int i=0;i<roleList.size();i++){
                Role item =roleList.get(i);
                if(i!=0)authorityStr.append(",");
                authorityStr.append("ROLE_").append(item.getRoleId());
            }
        }else{
            authorityStr.append("");
        }
//        HashSet<GrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("vip1"));
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorityStr.toString());
        User user =new User(username,new BCryptPasswordEncoder().encode(password),authorities);
        return user;
    }
}
