package test.com.lavrente.soundtrack;

import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.logic.Validator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by 123 on 26.01.2017.
 */
public class ValidationTest {
    private static Validator validator;

    @BeforeClass
    public static void initValidator() {
        validator = new Validator();
    }

    @Test
    public void checkIsBankCardValidFirst() {
        String card = "123";
        boolean actual = validator.isBankCardValid(card);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsBankCardValidSecond() {
        String card = "1234567891011121314151617";
        boolean actual = validator.isBankCardValid(card);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsLengthValidThird() {
        String card = "111222333444555";
        boolean actual = validator.isBankCardValid(card);
        Assert.assertTrue(actual);
    }

    @Test
    public void checkIsBonusValid() {
        String bonus = "120";
        boolean actual = validator.isBonusValid(bonus);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsTitleLengthValid() {
        String cash = "-1";
        boolean actual = validator.isCashValid(cash);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsEmailValidFirst() {
        String comment = "";
        boolean actual = validator.isCommentValid(comment);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsCommentValidSecond() {
        String comment = "11111111";
        boolean actual = validator.isCommentValid(comment);
        Assert.assertTrue(actual);
    }
}