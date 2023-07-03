package ru.omsu.imit.tails55.texgen.backend.converters;


import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.*;

public class ShefferConverter implements IConverter {


    private static FunctionTree convertToShefferStrokesStep1(FunctionTree functionTree) {
        int params = functionTree.getParams();
        if (!(functionTree instanceof BiOperator)) {
            if (functionTree instanceof Inverse)
                return new Inverse(convertToShefferStrokesStep1(((Inverse) functionTree).getInverseOf()));
            return functionTree;
        }
        FunctionTree left = convertToShefferStrokesStep1(((BiOperator) functionTree).getLeft());
        FunctionTree right = convertToShefferStrokesStep1(((BiOperator) functionTree).getRight());

        if (functionTree instanceof OrOperator)
            return new ShefferStrokeOperator(params, new Inverse(left), new Inverse(right));
        if (functionTree instanceof AndOperator)
            return new Inverse(new ShefferStrokeOperator(params, left, right));
        if (functionTree instanceof ImplicationOperator)
            return new ShefferStrokeOperator(params, left, new Inverse(right));
        if (functionTree instanceof ShefferStrokeOperator)
            return functionTree;
        if (functionTree instanceof PiercesArrowOperator)
            return new Inverse(new ShefferStrokeOperator(params, new Inverse(left), new Inverse(right)));
        if (functionTree instanceof EqualityOperator) //!(avb)v!(!av!b) = (!a&!b)v(a&b)
            return new ShefferStrokeOperator(params,
                    new ShefferStrokeOperator(params, new Inverse(left), new Inverse(right)),
                    new ShefferStrokeOperator(params, left, right));
        if (functionTree instanceof XorOperator) //!(!avb)v!(av!b) = (a&!b)v(!a&b)
            return new ShefferStrokeOperator(params,
                    new ShefferStrokeOperator(params, new Inverse(left), right),
                    new ShefferStrokeOperator(params, left, new Inverse(right)));
        return functionTree;
    }


    private static FunctionTree convertToShefferStrokesStep2(FunctionTree functionTree) {
        int params = functionTree.getParams();
        if (functionTree instanceof ShefferStrokeOperator)
            return new ShefferStrokeOperator(params,
                    convertToShefferStrokesStep2(((ShefferStrokeOperator) functionTree).getLeft()),
                    convertToShefferStrokesStep2(((ShefferStrokeOperator) functionTree).getRight()));
        else if (functionTree instanceof Inverse) {
            FunctionTree inverseOf = ((Inverse) functionTree).getInverseOf();
            if (inverseOf instanceof Inverse)
                return convertToShefferStrokesStep2(((Inverse) inverseOf).getInverseOf());
            else {
                inverseOf = convertToShefferStrokesStep2(inverseOf);
                return new ShefferStrokeOperator(params, inverseOf, inverseOf);
            }
        }
        return functionTree;
    }

    public String convert(FunctionTree tree, String letters) {
        return "$" + convertToShefferStrokesStep2(convertToShefferStrokesStep1(tree)) + "$";
    }
}
