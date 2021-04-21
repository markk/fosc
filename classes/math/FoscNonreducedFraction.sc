/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscNonreducedFraction


SUMMARY:: Returns a FoscNonreducedFraction.


DESCRIPTION:: TODO


USAGE::

'''

• FoscNonreducedFraction

code::
FoscNonreducedFraction(3).cs;
FoscNonreducedFraction(3.14159).cs;
FoscNonreducedFraction(#[3,2]).cs;
FoscNonreducedFraction(6, 2).cs;
FoscNonreducedFraction().cs;
'''
------------------------------------------------------------------------------------------------------------ */
FoscNonreducedFraction : FoscFraction {
    init { |argPair|
        pair = argPair;
        # numerator, denominator = pair;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • +

    code::
    a = FoscNonreducedFraction(3, 2) + FoscNonreducedFraction(1, 2);
    a.pair;

    code::
    a = FoscNonreducedFraction(3, 2) + 0.5;
    a.pair;

    code::
    a = 1.5 + FoscNonreducedFraction(1, 2);
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    + { |expr|
        ^(this.reduce + expr).withDenominator(denominator);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • -

    code::
    a = FoscNonreducedFraction(3, 2) - FoscNonreducedFraction(1, 2);
    a.pair;

    code::
    a = FoscNonreducedFraction(3, 2) - 0.5;
    a.pair;

    code::
    a = 1.5 - FoscNonreducedFraction(1, 2);
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *
    
    code::
    a = FoscNonreducedFraction(3, 2) * FoscNonreducedFraction(1, 2);
    a.pair;

    code::
    a = FoscNonreducedFraction(3, 2) * 0.5;
    a.pair;

    code::
    a = 1.5 * FoscNonreducedFraction(1, 2);
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // * { |expr|
    //     ^(this.reduce * expr).withDenominator(denominator);
    // }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • /
    
    code::
    a = FoscNonreducedFraction(3, 2) / FoscNonreducedFraction(1, 2);
    a.pair;`

    code::
    a = FoscNonreducedFraction(3, 2) / 0.5;
    a.pair;

    code::
    a = 1.5 / FoscNonreducedFraction(1, 2);
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // / { |expr|
    //     ^(this.reduce / expr).withDenominator(denominator);
    // }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • div

    code::
    a = FoscNonreducedFraction(3, 2).div(FoscNonreducedFraction(1, 2));
    a.pair;

    code::
    a = FoscNonreducedFraction(3, 2).div(0.5);
    a.pair;

    code::
    a = 1.5.div(FoscNonreducedFraction(1, 2));
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // div { |expr|
    //     ^this.reduce.div(expr).withDenominator(denominator);
    // }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • mod
    
    code::
    a = FoscNonreducedFraction(3, 2) % FoscNonreducedFraction(1, 2);
    a.pair;

    code::
    a = FoscNonreducedFraction(3, 2) % 0.5;
    a.pair;

    code::
    a = 1.5 % FoscNonreducedFraction(1, 2);
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • pow
    
    code::
    a = FoscNonreducedFraction(3, 2) ** FoscNonreducedFraction(2, 1);
    a.pair;

    code::
    a = FoscNonreducedFraction(3, 2) ** 2;
    a.pair;

    code::
    a = 1.5 ** FoscNonreducedFraction(2, 1);
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • abs
    
    code::
    a = FoscNonreducedFraction(-6, 4).abs;
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • neg
    
    code::
    a = FoscNonreducedFraction(6, 4).neg;
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • reciprocal

    code::
    a = FoscNonreducedFraction(6, 4).reciprocal;
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • asFloat

    code::
    FoscNonreducedFraction(3, 2).asFloat;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • ==
    
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a == a;     // true
    a == b;     // true
    a == c;     // false
    c == d;     // true
    d == c;     // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |expr|
        ^(this.reduce == expr);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • !=

    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a != a;     // false
    a != b;     // false
    a != c;     // true
    c != d;     // false
    d != c;     // false
    '''
    -------------------------------------------------------------------------------------------------------- */ 
    /* --------------------------------------------------------------------------------------------------------
    '''
    • <
    
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a < a;     // false
    a < b;     // false
    a < c;     // true
    c < d;     // false
    d < c;     // false
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • >
    
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a > a;     // false
    a > b;     // false
    a > c;     // true
    c > b;     // true
    b > c;     // false
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • <=
    
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a <= a;     // true
    a <= b;     // true
    a <= c;     // true
    c <= d;     // true
    d <= c;     // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • >=
    
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a >= a;     // true
    a >= b;     // true
    a >= c;     // false
    c >= d;     // true
    d >= c;     // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • reduce
    '''
    -------------------------------------------------------------------------------------------------------- */
    reduce {
        ^FoscFraction(numerator, denominator);
    }
}
