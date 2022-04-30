package cn.rmfield.website.controller.user;

import cn.rmfield.website.utils.RedisCache;
import cn.rmfield.website.utils.ResponseResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
class NoticeContent{
    String content;
}

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/user/api")
public class NoticeBoardPushController {
    @Autowired
    private RedisCache redisCache;

    @GetMapping("/notice-board")
    public ResponseResult get(){
        try {
            NoticeContent noticeContent = new NoticeContent();
            noticeContent.setContent(redisCache.getCacheObject("NoticeContent"));
            return new ResponseResult(0,"OK",noticeContent);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new ResponseResult(5,"获取公告失败");
        }
    }
}
