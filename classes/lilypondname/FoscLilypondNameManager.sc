/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscLilypondNameManager


SUMMARY:: Returns a FoscLilypondNameManager.


DESCRIPTION:: LilyPond name manager.

Base class from which grob, setting and tweak managers inherit.


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscLilypondNameManager {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <vars, cachedSelector;
    *new {
        ^super.new.init;
    }
    init {
        vars = IdentityDictionary(know: true);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • size

    !!!TODO: can this be deprecated ??

    Override method inherited from Object.
    -------------------------------------------------------------------------------------------------------- */
    size {
        ^this.doesNotUnderstand('size');
    }
    /* --------------------------------------------------------------------------------------------------------
    • doesNotUnderstand

    This allows for putting and accessing of attributes in vars through method calls to a FoscLilypondNameManager.

    '''
    code::
    a = FoscLilypondNameManager();
    a.color = 'red';
    a.size = 12;
    a.color.postln;

    code::
    a.size.postln;
    '''

    '''
    code::
    a = FoscNote(60, 1/4);
    m = override(a);
    m.noteHead.color = 'red';
    m.noteHead.size = 12;
    a.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    doesNotUnderstand { |selector, item|
        if (item.isNil) {
            cachedSelector = selector;
            if (vars[cachedSelector].isNil) {
                this.prSetState(((cachedSelector): FoscLilypondNameManager()));
            };
        } {
            selector = selector.asString.strip("_").asSymbol;
            if (cachedSelector.isNil) {
                this.prSetState(((selector): item));
            } {
                vars[cachedSelector].prSetState(((selector): item));
            };
        };
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • == (abjad: __eq__)

    Is true when expr is a LilyPond name manager with attribute pairs equal to
    those of this LilyPond name manager. Otherwise false.

    Returns true or false.

    '''
    code::
    m = FoscLilypondNameManager().prSetState((foo: 1, bar: 2));
    n = FoscLilypondNameManager().prSetState((foo: 1, bar: 2));
    (m == n).postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |expr|
    	if (expr.isKindOf(this.species)) {
    		^(this.prGetAttributePairs == expr.prGetAttributePairs);
    	};
    	^false;
    }
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString (abjad: __repr__)

    Gets interpreter representation of LilyPond name manager.

    Returns string.

    '''
    code::
    m = FoscLilypondNameManager();
    m.prSetState((foo: 1, bar: 2));
    m.asCompileString.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
    	var pairsStrings, pairs, varsString;
    	pairsStrings = [];
    	pairs = this.prGetAttributePairs;
    	varsString = pairs.do { |pair|
            pairsStrings = pairsStrings.add("'%': %".format(*pair););
        };
    	varsString = "".ccatList(pairsStrings)[2..];
        ^("%().prSetState((%))".format(this.species, varsString));
    }
    /* --------------------------------------------------------------------------------------------------------
    • put (abjad: __setattr__)

    Sets attribute 'name' of grob name manager to 'value'.
    -------------------------------------------------------------------------------------------------------- */
    put { |name, value|
        //!!!TODO: assert attribute name is valid grob name before setting value
        if (vars[value].isKindOf(FoscLilypondNameManager)) {
            vars[name].prSetState(value.vars);
        } {
            vars[name] = value;
        };
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetAttributePairs

    '''
    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
    b = override(a);
    b.noteHead.color = 'red';
    b.noteHead.size = 20;
    b.prGetAttributePairs;
    a.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetAttributePairs {
    	^vars.asSortedArray;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prSetState

    Sets object state.

    '''
    code::
    m = FoscLilypondNameManager();
    m.prSetState((foo: 1, bar: 2));
    m.prGetAttributePairs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prSetState { |state|
        vars.putAll(state);
    }
}
