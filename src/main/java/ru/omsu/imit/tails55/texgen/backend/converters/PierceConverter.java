package ru.omsu.imit.tails55.texgen.backend.converters;


import ru.omsu.imit.tails55.texgen.backend.binarylogic.functions.*;

public class PierceConverter implements IConverter {
    private static FunctionTree convertToPiercesArrowsStep1(FunctionTree functionTree) {
        int params = functionTree.getParams();
        if (!(functionTree instanceof BiOperator)) {
            if (functionTree instanceof Inverse)
                return new Inverse(convertToPiercesArrowsStep1(((Inverse) functionTree).getInverseOf()));
            return functionTree;
        }
        FunctionTree left = convertToPiercesArrowsStep1(((BiOperator) functionTree).getLeft());
        FunctionTree right = convertToPiercesArrowsStep1(((BiOperator) functionTree).getRight());

        if (functionTree instanceof AndOperator)
            return new PiercesArrowOperator(params, new Inverse(left), new Inverse(right));
        if (functionTree instanceof OrOperator)
            return new Inverse(new PiercesArrowOperator(params, left, right));
        if (functionTree instanceof ImplicationOperator)
            return new Inverse(new PiercesArrowOperator(params, new Inverse(left), right));
        if (functionTree instanceof PiercesArrowOperator)
            return functionTree;
        if (functionTree instanceof ShefferStrokeOperator)
            return new Inverse(new PiercesArrowOperator(params, new Inverse(left), new Inverse(right)));
        if (functionTree instanceof EqualityOperator) //!(a&!b)&!(!a&b)
            return new PiercesArrowOperator(params,
                    new PiercesArrowOperator(params, new Inverse(left), right),
                    new PiercesArrowOperator(params, left, new Inverse(right)));
        if (functionTree instanceof XorOperator) //!(a&b)&!(!a&!b)
            return new PiercesArrowOperator(params,
                    new PiercesArrowOperator(params, new Inverse(left), new Inverse(right)),
                    new PiercesArrowOperator(params, left, right));
        return functionTree;
    }


    private static FunctionTree convertToPiercesArrowsStep2(FunctionTree functionTree) {
        int params = functionTree.getParams();
        if (functionTree instanceof PiercesArrowOperator)
            return new PiercesArrowOperator(params,
                    convertToPiercesArrowsStep2(((PiercesArrowOperator) functionTree).getLeft()),
                    convertToPiercesArrowsStep2(((PiercesArrowOperator) functionTree).getRight()));
        else if (functionTree instanceof Inverse) {
            FunctionTree inverseOf = ((Inverse) functionTree).getInverseOf();
            if (inverseOf instanceof Inverse)
                return convertToPiercesArrowsStep2(((Inverse) inverseOf).getInverseOf());
            else {
                inverseOf = convertToPiercesArrowsStep2(inverseOf);
                return new PiercesArrowOperator(params, inverseOf, inverseOf);
            }
        }
        return functionTree;
    }


    public String convert(FunctionTree tree, String letters) {
        return "$" + convertToPiercesArrowsStep2(convertToPiercesArrowsStep1(tree)) + "$";
    }
}
