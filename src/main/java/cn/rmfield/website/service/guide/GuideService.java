package cn.rmfield.website.service.guide;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.utils.ResponseResult;

public interface GuideService {
    ResponseResult register(RfUser secUserDomain);
    ResponseResult login(RfUser rfUser);
    ResponseResult logout();
}
