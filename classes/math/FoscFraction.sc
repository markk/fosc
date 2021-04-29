/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscFraction


SUMMARY:: Returns a FoscFraction.


DESCRIPTION:: Fractions


USAGE::

'''
code::
FoscFraction(3).cs;

code::
FoscFraction(3.14159).cs;

code::
FoscFraction(#[3,2]).cs;

code::
FoscFraction(6,2).cs;

code::
FoscNonreducedFraction(6,2).cs;

code::
FoscFraction().cs;
'''
------------------------------------------------------------------------------------------------------------ */
FoscFraction : AbstractFunction {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    classvar <methodNames;
    var <numerator, <denominator, <pair;
    *initClass {
        methodNames = this.methods.collect { |each| each.name };
    }
    *new { |numerator, denominator|
        var pair;
        pair = if (denominator.isNil) {
            case
            { numerator.isKindOf(FoscFraction) } { numerator.pair }
            { numerator.isSequenceableCollection } { numerator }
            { numerator === inf } { [inf, 0] }
            { numerator === -inf } { [-inf, 0] }
            { numerator.isFloat } { numerator.asFraction }
            { numerator.isInteger } { [numerator, 1] }
            { numerator.respondsTo('duration') } { numerator.pair }
            { numerator.isNil } { #[0,1] };
        } {
            case
            { numerator.isKindOf(FoscFraction) && { denominator.isKindOf(FoscFraction) } } {
                (numerator / denominator).pair;
            }
            {
                [numerator, denominator];
            };
        };
        ^super.new.init(pair);
    }
    init { |argPair|
        pair = argPair.reduceFraction;
        # numerator, denominator = pair;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • +

    '''
    code::
    a = FoscFraction(3,2) + FoscFraction(1,2);
    a.str;
    '''
    '''
    code::
    a = FoscFraction(3,2) + 0.5;
    a.str;
    '''
    '''
    code::
    a = 1.5 + FoscFraction(1,2);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    + { |expr|
        var num, denom;
        expr = this.species.new(expr);
        num = (numerator * expr.denominator) + (expr.numerator * denominator);
        denom = denominator * expr.denominator;
        ^this.species.new(num, denom);
    }
    /* --------------------------------------------------------------------------------------------------------
    • -

    '''
    code::
    a = FoscFraction(3,2) - FoscFraction(1,2);
    a.str;
    '''
    '''
    code::
    a = FoscFraction(3,2) - 0.5;
    a.str;
    '''
    '''
    code::
    a = 1.5 - FoscFraction(1,2);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    - { |expr|
        expr = this.species.new(expr);
        ^(this + expr.neg);
    }
    /* --------------------------------------------------------------------------------------------------------
    • *

    '''
    code::
    a = FoscFraction(3,2) * FoscFraction(1,2);
    a.str;
    '''
    '''
    code::
    a = FoscFraction(3,2) * 0.5;
    a.str;
    '''
    '''
    code::
    a = 0.5 * FoscFraction(3,2);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    * { |expr|
        expr = this.species.new(expr);
        ^this.species.new(numerator * expr.numerator, denominator * expr.denominator);
    }
    /* --------------------------------------------------------------------------------------------------------
    • /

    '''
    code::
    a = FoscFraction(3,2) / FoscFraction(1,2);
    a.str;
    '''
    '''
    code::
    a = FoscFraction(3,2) / 0.5;
    a.str;
    '''
    '''
    code::
    a = 1.5 / FoscFraction(1,2);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    / { |expr|
        expr = this.species.new(expr);
        ^this.species.new(numerator * expr.denominator, denominator * expr.numerator);
    }
    /* --------------------------------------------------------------------------------------------------------
    • ==

    '''
    code::
    a = FoscFraction(3,2);
    b = FoscFraction(6,4);
    c = FoscFraction(2,1);
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
        if (expr.isNumber) { expr = FoscFraction(expr) };
        ^(numerator == expr.numerator && { denominator == expr.denominator });
    }
    /* --------------------------------------------------------------------------------------------------------
    • ===

    '''
    code::
    a = FoscFraction(3,2);
    b = FoscFraction(6,4);
    c = FoscFraction(2,1);
    d = 2;

    a === a;     // true

    code::
    a === b;     // true  !!!TODO: override for FoscNonreducedFraction

    code::
    a === c;     // false

    code::
    c === d;     // true

    code::
    d === c;     // true
    '''
    -------------------------------------------------------------------------------------------------------- */
    === { |expr|
        ^(this == expr);
    }
    /* --------------------------------------------------------------------------------------------------------
    • !=

    '''
    code::
    a = FoscFraction(3,2);
    b = FoscFraction(6,4);
    c = FoscFraction(2,1);
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
    != { |expr|
        ^(this == expr).not;
    }
    /* --------------------------------------------------------------------------------------------------------
    • <

    '''
    code::
    a = FoscFraction(3,2);
    b = FoscFraction(6,4);
    c = FoscFraction(2,1);
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
    < { |expr|
        ^(this.asFloat < expr.asFloat);
    }
    /* --------------------------------------------------------------------------------------------------------
    • >

    '''
    code::
    a = FoscFraction(3,2);
    b = FoscFraction(6,4);
    c = FoscFraction(2,1);
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
    > { |expr|
        ^(this.asFloat > expr.asFloat);
    }
    /* --------------------------------------------------------------------------------------------------------
    • <=

    '''
    code::
    a = FoscFraction(3,2);
    b = FoscFraction(6,4);
    c = FoscFraction(2,1);
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
    <= { |expr|
        ^(this.asFloat <= expr.asFloat);
    }
    /* --------------------------------------------------------------------------------------------------------
    • >=

    '''
    code::
    a = FoscFraction(3,2);
    b = FoscFraction(6,4);
    c = FoscFraction(2,1);
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
    >= { |expr|
        ^(this.asFloat >= expr.asFloat);
    }
    /* --------------------------------------------------------------------------------------------------------
    • abs

    '''
    code::
    a = FoscFraction(-3,2).abs;
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    abs {
        ^this.species.new(numerator.abs, denominator);
    }
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString

    '''
    code::
    a = FoscFraction(1,4);
    a.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^"%(%, %)".format(this.species, this.numerator, this.denominator);
    }
    /* --------------------------------------------------------------------------------------------------------
    • asFloat

    '''
    code::
    a = FoscFraction(3,2);
    a.asFloat;
    '''
    -------------------------------------------------------------------------------------------------------- */
    asFloat {
        ^(numerator / denominator);
    }
    /* --------------------------------------------------------------------------------------------------------
    • asInteger

    '''
    code::
    a = FoscFraction(5,2);
    a.asInteger;
    '''
    -------------------------------------------------------------------------------------------------------- */
    asInteger {
        ^this.asFloat.asInteger;
    }
    /* --------------------------------------------------------------------------------------------------------
    • copy
    -------------------------------------------------------------------------------------------------------- */
    copy {
        ^this; // immutable
    }
    /* --------------------------------------------------------------------------------------------------------
    • deepCopy
    -------------------------------------------------------------------------------------------------------- */
    deepCopy {
        ^this; // immutable
    }
    /* --------------------------------------------------------------------------------------------------------
    • div

    '''
    code::
    a = FoscFraction(3,2).div(FoscFraction(1,2));
    a.str;

    code::
    a = FoscFraction(3,2).div(0.5);
    a.str;

    code::
    a = 1.5.div(FoscFraction(1,2));
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    div { |expr|
        var div, result;
        expr = this.species.new(expr);
        div = this / expr;
        result = this.species.new(div.numerator.div(div.denominator));
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    • hash

    '''
    code::
    a = FoscFraction([1,4]);
    b = FoscFraction([1,4]);
    a.hash;

    code::
    a.hash == b.hash;
    '''
    -------------------------------------------------------------------------------------------------------- */
    hash {
        ^(this.asFloat).hash;
    }
    /* --------------------------------------------------------------------------------------------------------
    • mod

    '''
    code::
    a = FoscFraction(3,2) % FoscFraction(1,2);
    a.str;

    code::
    a = FoscFraction(3,2) % 0.5;
    a.str;

    code::
    a = 1.5 % FoscFraction(1,2);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    mod { |expr|
        var div, result;
        expr = this.species.new(expr);
        //div = this.div(expr);
        //result = (this - expr) * div;
        result = this.asFloat % expr.asFloat;
        result = this.species.new(result);
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    • neg

    '''
    code::
    a = FoscFraction(3,2).neg;
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    neg {
        ^this.species.new(numerator.neg, denominator);
    }
    /* --------------------------------------------------------------------------------------------------------
    • pow

    '''
    code::
    a = FoscFraction(3,2) ** FoscFraction(2,1);
    a.str;

    code::
    a = FoscFraction(3,2) ** 2;
    a.str;

    code::
    a = 1.5 ** FoscFraction(2,1);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    pow { |expr|
        ^this.species.new(this.asFloat ** expr.asFloat)
    }
    /* --------------------------------------------------------------------------------------------------------
    • reciprocal

    '''
    code::
    a = FoscFraction(3,2);
    a.reciprocal.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    reciprocal {
        ^this.species.new(denominator, numerator);
    }
    /* --------------------------------------------------------------------------------------------------------
    • sign

    '''
    code::
    a = FoscFraction(3,4);
    a.sign;

    code::
    a = FoscFraction(-3,4);
    a.sign;
    '''
    -------------------------------------------------------------------------------------------------------- */
    sign {
        ^numerator.sign;
    }
    /* --------------------------------------------------------------------------------------------------------
    • storeArgs
    -------------------------------------------------------------------------------------------------------- */
    storeArgs {
        ^[numerator, denominator];
    }
    /* --------------------------------------------------------------------------------------------------------
    • str

    '''
    code::
    a = FoscFraction(3,2);
    a.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^"%/%".format(numerator, denominator);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • withDenominator

    '''
    code::
    a = FoscNonreducedFraction(3,2);
    a = a.withDenominator(4);
    a.pair;
    '''
    -------------------------------------------------------------------------------------------------------- */
    withDenominator { |denominator|
        var currentNumerator, currentDenominator, multiplier, newNumerator, newDenominator, pair;
        # currentNumerator, currentDenominator = this.pair;
        multiplier = FoscMultiplier(denominator, currentDenominator);
        newNumerator = multiplier * currentNumerator;
        newDenominator = multiplier * currentDenominator;
        if (newNumerator.denominator == 1 && (newDenominator.denominator == 1)) {
            pair = [newNumerator.numerator, newDenominator.numerator];
        } {
            pair = [currentNumerator, currentDenominator];
        };
        ^FoscNonreducedFraction(*pair);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • performBinaryOpOnSimpleNumber
    -------------------------------------------------------------------------------------------------------- */
    performBinaryOpOnSimpleNumber { |selector, number|
        if (FoscFraction.methodNames.includes(selector).not) {
            throw("% does not implement: %.".format(this.species, selector));
        };
        ^this.species.new(number).perform(selector, this);
    }
}
