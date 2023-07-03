package ru.omsu.imit.tails55.texgen.backend.binarylogic;

import org.junit.jupiter.api.Test;
import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.*;
import ru.omsu.imit.tails55.texgen.backend.converters.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {

    @Test
    void testConverters() {
        FunctionTree function = new AndOperator(2,
                new OrOperator(2, new Symbol(2, 0, 'a'), new Inverse(new Symbol(2, 1, 'b'))),
                new ShefferStrokeOperator(2, new Symbol(2, 0, 'a'), new Symbol(2, 0, 'a')));

        DNFConverter dnfConverter = new DNFConverter();
        assertEquals(dnfConverter.convert(function, "ab"), "$\\lnot b\\land \\lnot a$");

        CNFConverter cnfConverter = new CNFConverter();
        assertEquals(cnfConverter.convert(function, "ab"), "$(\\lnot b\\lor a)\\land (b\\lor \\lnot a)\\land (\\lnot b\\lor \\lnot a)$");

        ToStringConverter toStringConverter = new ToStringConverter();
        assertEquals(toStringConverter.convert(function, "ab"), "$(a\\lor \\lnot b)\\land a\\,|\\allowbreak\\,a$");

        PierceConverter pierceConverter = new PierceConverter();
        assertEquals(pierceConverter.convert(function, "ab"), "$(a\\downarrow (b\\downarrow b))\\downarrow ((a\\downarrow a)\\downarrow (a\\downarrow a))$");

        ShefferConverter shefferConverter = new ShefferConverter();
        assertEquals(shefferConverter.convert(function, "ab"), "$(((a\\,|\\allowbreak\\,a)\\,|\\allowbreak\\,b)\\,|\\allowbreak\\,(a\\,|\\allowbreak\\,a))\\,|\\allowbreak\\,(((a\\,|\\allowbreak\\,a)\\,|\\allowbreak\\,b)\\,|\\allowbreak\\,(a\\,|\\allowbreak\\,a))$");

        ZhegalkinConverter zhegalkinConverter = new ZhegalkinConverter();
        assertEquals(zhegalkinConverter.convert(function,"ab"),"$ab+a+b+1$");
    }
}