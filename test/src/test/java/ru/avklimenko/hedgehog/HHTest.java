package ru.avklimenko.hedgehog;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static ru.avklimenko.hedgehog.Utils.map;

public class HHTest extends Assert {
    private final static String connectionStringSQLite  = "jdbc:sqlite:"
            + (System.getProperty("user.dir") + "/src/test/resources/ru/avklimenko/hedgehog/hedgehog.sqlite")
            .replaceAll("[/]", "\\" + File.separator);
    private final static String packageName = "ru/avklimenko/hedgehog/";
    private static final HHExecutor executor = new HHExecutor(connectionStringSQLite, packageName);

    @BeforeClass
    void createTempTable() {
        UpdateResult updateResult = executor.updateFromResource("create_temp_table.sql", null);
        updateResult.join();
        assertEquals(updateResult.getRowsAffected(), 0);
        updateResult = executor.update("INSERT INTO TMP_TABLE (id, text) VALUES(1, 'ssdd')");
        updateResult.join();
        assertEquals(updateResult.getRowsAffected(), 1);
    }

    @AfterClass
    void dropTempTable() {
        UpdateResult updateResult = executor.updateFromResource("drop_temp_table.sql", null);
        updateResult.join();
        assertEquals(updateResult.getRowsAffected(), 0);
    }

    @DataProvider
    public Object[][] selectAccByCystTypeTestData() {
        return new Object[][] {
                {"I", 19, 14, 15, "2001-05-23", "CHK", 3487.19},
                {"B", 5, 3, 23, "2003-07-30", "CHK", 38552.05}
        };
    }

    @Test(dataProvider = "selectAccByCystTypeTestData")
    void selectAccByCystTypeTest(String custType, Integer rowCount, Integer rowNum, Integer accountId, String openDate, String productCD, Double availBalance) {
        SelectResult result = executor.selectFromResource("select_acc_by_cust_type.sql", map("cust_type", custType));
        assertEquals(result.getRowsCount(), rowCount.intValue());
        List<Object> row = result.getRow(rowNum);
        assertEquals(row.get(0), accountId);
        assertEquals(row.get(1), openDate);
        assertEquals(row.get(2), productCD);
        assertEquals(row.get(3), availBalance);
    }
}
