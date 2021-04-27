/* ------------------------------------------------------------------------------------------------------------
TITLE:: FoscMultiplier


SUMMARY:: Returns a FoscMultiplier.


DESCRIPTION:: TODO


USAGE::

FoscMultiplier

'''
Initializes from integer numerator.

code::
FoscMultiplier(3).format;
'''

'''
Initializes from integer numerator and denominator.

code::
FoscMultiplier(3, 16).format;
'''

'''
Initializes from integer-equivalent numeric numerator.

code::
FoscMultiplier(3.0).format;
'''

'''
Initializes from integer-equivalent numeric numerator and denominator.

FIXME: returns ERROR: Array:reduceFraction: items in receiver must be integers: 3.0.

code::
FoscMultiplier(3.0, 16).format;
nointerpret
'''

'''
Initializes from integer-equivalent singleton.

code::
FoscMultiplier(3).format;
'''

'''
Initializes from integer-equivalent pair.

code::
FoscMultiplier([3, 16]).format;
'''

'''
Initializes from other duration.

code::
FoscMultiplier(FoscDuration(3, 16)).format
'''

'''
Intializes from fraction.

code::
FoscMultiplier(FoscFraction(3, 16)).format;
'''

'''
Initializes from nonreduced fraction.

code::
m = FoscMultiplier(FoscNonreducedFraction(6, 32))
m.pair.postln; //!!!! BROKEN
'''

'''
FoscMultipliers inherit from built-in fraction.

code::
FoscMultiplier(3, 16).isKindOf(FoscFraction).postln; // true
'''

'''
FoscMultipliers are numbers.

code::
FoscMultiplier(3, 16).isKindOf(Number).postln; //!!! BROKEN
'''

'''
Attaching a multiplier to a score component multiplies that component's duration.

FIXME: this doesn't seem to work...

code::
n = FoscNote(60, 1);
n.attach(FoscMultiplier(5, 8));
n.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscMultiplier : FoscDuration {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • *

    Multiplier times duration returns duration.

    Returns duration.

    '''
    code::
    a = FoscMultiplier(1, 1);
    b = FoscMultiplier(1, 3);
    c = FoscDuration(1, 3);

    (a * b).class.postln;        // returns a FoscMultiplier

    code::
    (a * c).class.postln;        // returns a FoscDuration
    '''

    def __mul__(self, *arguments):
    if len(arguments) == 1 and type(arguments[0]) is Duration:
        return Duration(Duration.__mul__(self, *arguments))
    else:
        return Duration.__mul__(self, *arguments)

    -------------------------------------------------------------------------------------------------------- */
    * { |expr|
        if (expr.species == FoscDuration) {
            ^(FoscDuration(this) * expr);
        } {
            ^this.species.new(FoscDuration(this) * expr);
        };
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC CLASS PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • *fromDotCount

    Makes multiplier from 'dotCount'.

    '''
    code::
    FoscMultiplier.fromDotCount(0).str;

    code::
    FoscMultiplier.fromDotCount(1).str;

    code::
    FoscMultiplier.fromDotCount(2).str;

    code::
    FoscMultiplier.fromDotCount(3).str;

    code::
    FoscMultiplier.fromDotCount(4).str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    *fromDotCount { |dotCount|
        var denominator, numerator;
        assert(dotCount.isInteger);
        assert(0 <= dotCount);
        denominator = (2 ** dotCount).asInteger;
        numerator = (2 ** (dotCount + 1) - 1).asInteger;
        ^FoscMultiplier(numerator, denominator);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • isNormalized

    Is true when mutliplier is greater than 1/2 and less than 2. Otherwise false.

    Returns true or false.

    '''
    code::
    FoscMultiplier(3, 2).isNormalized.postln;

    code::
    FoscMultiplier(7, 2).isNormalized.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    isNormalized {
        ^((this.species.new(1, 2) < this) && (this < this.species.new(2)));
    }
}
