package WizardTD;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest extends App
{
    Result r = new Result(this);

    @Test
    void Test1()
    {
        r.setPressR(false);
        assertEquals(false, r.isWin());
    }
}