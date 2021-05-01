/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscOrdinalConstant


SUMMARY:: Returns a FoscOrdinalConstant.


DESCRIPTION:: An ordinal constant.

```
representations = #[\left, \right, \center, \up, \down, \less, \more, \exact];
```

USAGE::

'''
code::
a = FoscOrdinalConstant('x', -1);
b = FoscOrdinalConstant('x', 1);
a < b;

code::nointerpret
a = FoscOrdinalConstant('x', 0);
b = FoscOrdinalConstant('y', 1);
a < b;

post::
ERROR: can only compare like-dimensioned ordinal constants.
'''
------------------------------------------------------------------------------------------------------------ */
FoscOrdinalConstant : FoscObject {
    var <dimension, <value, <representation;
    *new { |dimension, value=0, representation|
        ^super.new.init(dimension, value, representation);
    }
    init { |argDimension, argValue, argRepresentation|
        dimension = argDimension.asSymbol ? '';
        value = argValue;
        representation = argRepresentation.asSymbol ? '';
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • <
    -------------------------------------------------------------------------------------------------------- */
    < { |expr|
        this.checkComparator(expr);
        ^(value < expr.value);
    }
    /* --------------------------------------------------------------------------------------------------------
    • >
    -------------------------------------------------------------------------------------------------------- */
    > { |expr|
        this.checkComparator(expr);
        ^(value < expr.value);
    }
    /* --------------------------------------------------------------------------------------------------------
    • <=
    -------------------------------------------------------------------------------------------------------- */
    <= { |expr|
        this.checkComparator(expr);
        ^(value <= expr.value);
    }
    /* --------------------------------------------------------------------------------------------------------
    • >=
    -------------------------------------------------------------------------------------------------------- */
    >= { |expr|
        this.checkComparator(expr);
        ^(value >= expr.value);
    }
    /* --------------------------------------------------------------------------------------------------------
    • ==
    -------------------------------------------------------------------------------------------------------- */
    == { |expr|
        this.checkComparator(expr);
        ^(value == expr.value);
    }
    /* --------------------------------------------------------------------------------------------------------
    • !=
    -------------------------------------------------------------------------------------------------------- */
    != { |expr|
        this.checkComparator(expr);
        ^(value != expr.value);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • checkComparator
    -------------------------------------------------------------------------------------------------------- */
    checkComparator { |expr|
        if (expr.isKindOf(FoscOrdinalConstant).not || { dimension != expr.dimension }) {
            Error("can only compare like-dimensioned ordinal constants.").throw;
        };
    }
}
