package yhsoft.tax.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yhsoft.tax.TaxApplication;
import yhsoft.tax.modules.setting.model.DictionaryItemInfo;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaxApplication.class)
public class DictionaryUtilsTest {

    @Test
    public void getItemList() {
        List<DictionaryItemInfo> itemList = DictionaryUtils.getItemList("dbtype");
        itemList.forEach(c -> {
            System.out.println(c.getCode() + ":" + c.getText());
        });
    }
}