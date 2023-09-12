package org.example.client;

import org.example.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("adminserver") //指定服务名
public interface AdminClient {

    @GetMapping("/admins/selectAll/{currentPage}/{pageSize}")
    Result selectAll(@PathVariable Integer currentPage, @PathVariable Integer pageSize);

    //根据aId查询用户
    @GetMapping("/admins/selectByAid/{aId}")
    Result selectByAid(@PathVariable Integer aId);

    @GetMapping("/admins/selectAllAdminNum")
    Result selectAllAdminNum();

}
