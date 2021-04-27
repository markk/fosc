/* --------------------------------------------------------------------------------------------------------
TITLE:: FoscOffset


SUMMARY:: Returns a FoscOffset.


DESCRIPTION:: TODO


USAGE::

FoscOffset

'''
Initializes from integer numerator:

code::
FoscOffset(3, 1).format;
'''
'''
Initializes from integer numerator and denominator:

code::
FoscOffset(3, 16).format;
'''
'''
Initializes from integer-equivalent numeric numerator:

code::
FoscOffset(3.0).format;
'''
'''
Initializes from integer-equivalent numeric numerator and denominator:

FIXME: returns ERROR: Array:reduceFraction: items in receiver must be integers: 3.0.

code::
FoscOffset(3.0, 16).format;
nointerpret
'''
'''
Initializes from integer-equivalent singleton:

code::
FoscOffset(3).format;
'''
'''
Initializes from integer-equivalent pair:

code::
FoscOffset([3, 16]).format;
'''
'''
Initializes from duration:

code::
FoscOffset(FoscDuration(3, 16)).format;
'''
'''
Initializes from other offset:

code::
FoscOffset(FoscOffset(3, 16)).format;
'''
'''
Initializes from other offset with grace displacement:

code::
a = FoscOffset([3, 16], graceDisplacement: [-1, 16]);
FoscOffset(a).format;
'''
'''
Intializes from fraction:

code::
FoscOffset(FoscFraction(3, 16)).format;
'''
'''
Initializes from solidus string:

FIXME ERROR: String:reduceFraction: receiver size must be 2: 4.

code::
FoscOffset("3/16").format;
nointerpret
'''
'''
Initializes from nonreduced fraction:

FIXME: ERROR: syntax error, unexpected CLASSNAME, expecting NAME or WHILE or '[' or '('

code::
FoscOffset(mathtools.NonreducedFraction(6, 32)).format;
nointerpret
'''
'''
FoscOffsets inherit from built-in fraction:

FIXME: surely duration?

code::
FoscOffset(3, 16).isKindOf(FoscDuration).postln;
'''
'''
FoscOffsets are numeric:

FIXME: not sure of an example for this.

code::
FoscOffset(3, 16);
'''
-------------------------------------------------------------------------------------------------------- */
FoscOffset : FoscDuration {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INIT
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* --------------------------------------------------------------------------------------------------------
	•
	__slots__ = (
	        '_grace_displacement',
	        )

	    ### CONSTRUCTOR ###

	    def __new__(class_, *args, **kwargs):
	        grace_displacement = None
	        for arg in args:
	            if hasattr(arg, 'grace_displacement'):
	                grace_displacement = getattr(arg, 'grace_displacement')
	                break
	        grace_displacement = grace_displacement or kwargs.get(
	            'grace_displacement')
	        if grace_displacement is not None:
	            grace_displacement = Duration(grace_displacement)
	        grace_displacement = grace_displacement or None
	        if len(args) == 1 and isinstance(args[0], Duration):
	            args = args[0].pair
	        self = Duration.__new__(class_, *args)
	        self._grace_displacement = grace_displacement
	        return self
	-------------------------------------------------------------------------------------------------------- */
	var <graceDisplacement;
	*new { |numerator, denominator, graceDisplacement|
		^super.new(numerator, denominator).initFoscOffset(graceDisplacement);
	}
	initFoscOffset { |argGraceDisplacement|
		graceDisplacement = argGraceDisplacement;
		if (graceDisplacement.notNil) { graceDisplacement = FoscDuration(graceDisplacement) };
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC INSTANCE METHODS: Special Methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • copy
    '''
    code::
    a = FoscOffset(FoscDuration(3, 16), graceDisplacement: [1, 16]);
    a.copy;
    a.pair.postln;

    code::
    a.graceDisplacement.pair.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • deepCopy
    -------------------------------------------------------------------------------------------------------- */

    /* --------------------------------------------------------------------------------------------------------
    • ==

    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a == b).postln;
    '''
    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 8]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a == b).postln;
    '''
    '''
    code::
    a = FoscOffset(1, 4);
    b = FoscOffset(2, 4);
    (a == b).postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |expr|
    	if (expr.isKindOf(this.species) && { this.pair == expr.pair }) {
    		if (graceDisplacement.notNil && expr.graceDisplacement.notNil) {
    			^(graceDisplacement == expr.graceDisplacement);
    		};
    	};
    	^(FoscDuration(this) == FoscDuration(expr));
    }
    /* --------------------------------------------------------------------------------------------------------
    • >=
    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a >= b).postln;
    '''
    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 8]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a >= b).postln;
    '''
    '''
    code::
    a = FoscOffset(1, 4);
    b = FoscOffset(2, 4);
    (a >= b).postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    >= { |expr|
    	if (expr.isKindOf(this.species) && { this.pair == expr.pair }) {
    		if (graceDisplacement.notNil && expr.graceDisplacement.notNil) {
    			^(graceDisplacement >= expr.graceDisplacement);
    		};
    	};
    	^(FoscDuration(this) >= FoscDuration(expr));
    }
    /* --------------------------------------------------------------------------------------------------------
    • storeArgs
    -------------------------------------------------------------------------------------------------------- */
    storeArgs {
        ^this.pair;
    }
    /* --------------------------------------------------------------------------------------------------------
    • >

    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a > b).postln;
    '''
    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 8]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a > b).postln;
    '''
    '''
    code::
    a = FoscOffset(2, 4);
    b = FoscOffset(1, 4);
    (a > b).postln;

    code::
    (a > a).postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    > { |expr|
    	if (expr.isKindOf(this.species) && { this.pair == expr.pair }) {
    		if (graceDisplacement.notNil && expr.graceDisplacement.notNil) {
    			^(graceDisplacement > expr.graceDisplacement);
    		};
    	};
    	^(FoscDuration(this) > FoscDuration(expr));
    }
    /* --------------------------------------------------------------------------------------------------------
    • hash
    -------------------------------------------------------------------------------------------------------- */

    /* --------------------------------------------------------------------------------------------------------
    • <=
    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a <= b).postln;
    '''
    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 8]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a <= b).postln;
    '''
    '''
    code::
    a = FoscOffset(2, 4);
    b = FoscOffset(1, 4);
    (a <= b).postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    <= { |expr|
    	if (expr.isKindOf(this.species) && { this.pair == expr.pair }) {
    		if (graceDisplacement.notNil && expr.graceDisplacement.notNil) {
    			^(graceDisplacement <= expr.graceDisplacement);
    		};
    	};
    	^(FoscDuration(this) <= FoscDuration(expr));
    }
    /* --------------------------------------------------------------------------------------------------------
    • <
    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a < b).postln;
    '''
    '''
    code::
    a = FoscOffset(1, 4, graceDisplacement: [-1, 8]);
    b = FoscOffset(1, 4, graceDisplacement: [-1, 16]);
    (a < b).postln;
    '''
    '''
    code::
    a = FoscOffset(2, 4);
    b = FoscOffset(1, 4);
    (a < b).postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    < { |expr|
    	if (expr.isKindOf(this.species) && { this.pair == expr.pair }) {
    		if (graceDisplacement.notNil && expr.graceDisplacement.notNil) {
    			^(graceDisplacement < expr.graceDisplacement);
    		};
    	};
    	^(FoscDuration(this) < FoscDuration(expr));
    }
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString
    -------------------------------------------------------------------------------------------------------- */

    /* --------------------------------------------------------------------------------------------------------
    • -
    -------------------------------------------------------------------------------------------------------- */
    - { |expr|
    	if (expr.isKindOf(this.species)) { ^(FoscDuration(this) - FoscDuration(expr)) };
    	if (expr.isKindOf(FoscDuration)) { ^(FoscDuration(this) - expr) };
    	expr = this.species.new(expr);
    	^(this - expr);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Additional
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • exclusivelyBetween
    '''
    code::
    a = FoscOffset(1, 4);
    b = FoscOffset(4, 4);
    c = FoscOffset(2, 4);
    c.exclusivelyBetween(a, b).postln;

    code::
    a.exclusivelyBetween(a, b).postln;

    code::
    a.exclusivelyBetween(b, c).postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    exclusivelyBetween { |a, b|
    	^(this > a && { this < b });
    }
    /* --------------------------------------------------------------------------------------------------------
    • inclusivelyBetween
    '''
    code::
    a = FoscOffset(1, 4);
    b = FoscOffset(4, 4);
    c = FoscOffset(2, 4);
    c.inclusivelyBetween(a, b).postln;

    code::
    a.inclusivelyBetween(a, b).postln;

    code::
    a.inclusivelyBetween(b, c).postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    inclusivelyBetween { |a, b|
    	^(this >= a && { this <= b });
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    •
    def _get_format_specification(self):
            is_indented = False
            names = []
            values = [self.numerator, self.denominator]
            if self._get_grace_displacement():
                is_indented = True
                names = ['grace_displacement']
                values = [(self.numerator, self.denominator)]
            return systemtools.FormatSpecification(
                client=self,
                repr_is_indented=is_indented,
                storage_format_args_values=values,
                storage_format_is_indented=is_indented,
                storage_format_kwargs_names=names,
                )
    '''
    -------------------------------------------------------------------------------------------------------- */

    /* --------------------------------------------------------------------------------------------------------
    '''
    •
    def _get_grace_displacement(self):
            from abjad.tools import durationtools
            if self.grace_displacement is None:
                return durationtools.Duration(0)
            return self.grace_displacement
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • graceDisplacement

    Gets grace displacement.

    Defaults to nil.

    Set to duration or nil.

    Returns duration or nil.

    '''
    Gets grace displacement equal to nil

    code::
    a = FoscOffset(1, 4);
    a.graceDisplacement.isNil.postln;
    '''
    '''
    Gets grace displacement equal to a negative sixteenth

    code::
    a = FoscOffset([1, 4], graceDisplacement: [-1, 16]);
    a.graceDisplacement.format;
    '''
    '''
    Stores zero-valued grace displacement as nil

    code::
    a = FoscOffset([1, 4], graceDisplacement: nil);
    a.graceDisplacement.isNil.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
}
