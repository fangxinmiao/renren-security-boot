package io.renren.api;

import io.renren.service.TokenService;
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

import java.util.Map;

/**
 * API登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/api")
public class ApiLoginController {
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public ApiLoginController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * 登录
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation(value = "登录", notes = "登录说明")
    @IgnoreAuth
    public R login(
            @ApiParam(value = "手机号") @RequestParam String mobile,
            @ApiParam(value = "密码") @RequestParam String password) {
        if (StringUtils.isBlank(mobile)) {
            return R.error(400, "手机号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return R.error(400, "密码不能为空");
        }

        // 用户登录
        long userId = userService.login(mobile, password);

        // 生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return R.ok(map);
    }
}
