package yhsoft.tax.modules.common.controller;

import com.yhsoft.common.web.util.ValidateCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zhuang on 2/26/2018.
 */
@Controller
@RequestMapping(value = "/common/validate-code")
public class ValidateCodeController {

    @RequestMapping(value = "getValidateCodeImage",method = RequestMethod.GET)
    public void getValidateCodeImage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        int codeLength = 4;
        int width = 80;
        int height = 30;
        String code = ValidateCodeUtils.getRandomCode(codeLength);
        BufferedImage bufferedImage = ValidateCodeUtils.generateValidateCodeImage(code, width, height);
        request.getSession().setAttribute(ValidateCodeUtils.VALIDATE_CODE_KEY, code);
        //CookieUtils.setCookie(response,VALIDATE_CODE_KEY, EncryptionUtils.encryptByMD5(code));
        OutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImage, "JPEG", out);
        out.close();
    }

}
