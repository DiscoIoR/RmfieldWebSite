package cn.rmfield.website.controller.admin;

import cn.rmfield.website.utils.RedisCache;
import cn.rmfield.website.utils.ResponseResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Data
class NoticeContent{
    String content;
}

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/api")
public class NoticeBoardUpdateController {
    @Autowired
    private RedisCache redisCache;

    @PostMapping("/notice-board")
    public ResponseResult update(@RequestBody NoticeContent noticeContent){
        try {
            redisCache.setCacheObject("NoticeContent",noticeContent.getContent());
            return new ResponseResult(0,"OK");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(5,"更新公告失败");
        }
    }
}
