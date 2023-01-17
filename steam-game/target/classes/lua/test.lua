
local amount = redis.call('get',KEYS[1])
local ismember = redis.call('sismember',KEYS[2],KEYS[3])
if(tonumber(amount)>0 and tonumber(ismember)==0)
then
    redis.call('decr',KEYS[1])
    redis.call('sadd',KEYS[2],KEYS[3])
    return 1;
else
    return 0;
end