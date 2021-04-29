/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscNonreducedFraction


SUMMARY:: Returns a FoscNonreducedFraction.


DESCRIPTION:: Non-reduced fractions


USAGE::

'''
code::
FoscNonreducedFraction(3).cs;

code::
FoscNonreducedFraction(3.14159).cs;

code::
FoscNonreducedFraction(#[3,2]).cs;

code::
FoscNonreducedFraction(6, 2).cs;

code::
FoscNonreducedFraction().cs;
'''
------------------------------------------------------------------------------------------------------------ */
FoscNonreducedFraction : FoscFraction {
    init { |argPair|
        pair = argPair;
        # numerator, denominator = pair;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • +

    '''
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
    • -

    '''
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
    • *

    '''
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
    • /

    '''
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
    • div

    '''
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
    • mod

    '''
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
    • pow

    '''
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
    • abs

    '''
    code::
    a = FoscNonreducedFraction(-6, 4).abs;
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • neg

    '''
    code::
    a = FoscNonreducedFraction(6, 4).neg;
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • reciprocal

    '''
    code::
    a = FoscNonreducedFraction(6, 4).reciprocal;
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • asFloat

    '''
    code::
    FoscNonreducedFraction(3, 2).asFloat;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • ==

    '''
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a == a;     // true

    code::
    a == b;     // true

    code::
    a == c;     // false

    code::
    c == d;     // true

    code::
    d == c;     // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |expr|
        ^(this.reduce == expr);
    }
    /* --------------------------------------------------------------------------------------------------------
    • !=

    '''
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a != a;     // false

    code::
    a != b;     // false

    code::
    a != c;     // true

    code::
    c != d;     // false

    code::
    d != c;     // false
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • <

    '''
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a < a;     // false

    code::
    a < b;     // false

    code::
    a < c;     // true

    code::
    c < d;     // false

    code::
    d < c;     // false
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • >

    '''
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a > a;     // false

    code::
    a > b;     // false

    code::
    a > c;     // true

    code::
    c > b;     // true

    code::
    b > c;     // false
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • <=

    '''
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a <= a;     // true

    code::
    a <= b;     // true

    code::
    a <= c;     // true

    code::
    c <= d;     // true

    code::
    d <= c;     // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • >=

    '''
    code::
    a = FoscNonreducedFraction(3, 2);
    b = FoscNonreducedFraction(6, 4);
    c = FoscNonreducedFraction(2, 1);
    d = 2;

    a >= a;     // true

    code::
    a >= b;     // true

    code::
    a >= c;     // false

    code::
    c >= d;     // true

    code::
    d >= c;     // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • reduce
    -------------------------------------------------------------------------------------------------------- */
    reduce {
        ^FoscFraction(numerator, denominator);
    }
}
