package io.renren.api;

import io.renren.service.UserService;
import io.renren.utils.R;
import io.renren.utils.annotation.IgnoreAuth;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/api")
public class ApiRegisterController {
    private final UserService userService;

    @Autowired
    public ApiRegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ApiOperation(value = "注册")
    @IgnoreAuth
    public R register(
            @ApiParam(value = "手机号") @RequestParam String mobile,
            @ApiParam(value = "密码") @RequestParam String password) {
        if (StringUtils.isBlank(mobile)) {
            return R.error(400, "手机号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return R.error(400, "密码不能为空");
        }

        userService.save(mobile, password);
        return R.ok();
    }
}
