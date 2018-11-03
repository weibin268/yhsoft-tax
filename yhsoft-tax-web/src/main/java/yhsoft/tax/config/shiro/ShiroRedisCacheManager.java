package yhsoft.tax.config.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by zhuang on 2/27/2018.
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {

    private RedisTemplate<byte[], byte[]> shiroRedisTemplate;

    public ShiroRedisCacheManager(RedisTemplate<byte[], byte[]> shiroRedisTemplate) {
        this.shiroRedisTemplate = shiroRedisTemplate;
    }

    @Override
    protected Cache<byte[], byte[]> createCache(String name) throws CacheException {
        return new ShiroRedisCache<>(name, shiroRedisTemplate);
    }
}