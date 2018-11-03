package yhsoft.tax.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yhsoft.tax.modules.setting.model.DictionaryItemInfo;
import yhsoft.tax.modules.setting.service.DictionaryService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zhuang
 * @create 6/17/18 6:36 PM
 **/
@Component
public class DictionaryUtils {

    private static DictionaryUtils dictionaryUtils;

    @Autowired
    private DictionaryService dictionaryService;

    @PostConstruct
    public void init() {
        dictionaryUtils = this;
    }

    public static List<DictionaryItemInfo> getItemList(String code) {
        return dictionaryUtils.dictionaryService.getDictionaryItemInfoListByCode(code);
    }

}
