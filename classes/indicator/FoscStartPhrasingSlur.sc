/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscStartPhrasingSlur


SUMMARY:: Returns a FoscStartPhrasingSlur.


DESCRIPTION:: Start a phrasing slur


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscStartPhrasingSlur : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <direction, <tweaks;
    var <context='Voice', <parameter='PHRASING_SLUR', <persistent=true, <publishStorageFormat=true;
    *new { |direction, tweaks|
        if (direction.notNil) { direction = direction.toTridirectionalLilypondSymbol };
        ^super.new.init(direction, tweaks);
    }
    init { |argDirection, argTweaks|
        direction = argDirection;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • context

    Gets context. Returns 'Voice'.

    '''
    code::
    a = FoscStartPhrasingSlur();
    a.context.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • direction

    Gets direction.

    '''
    code::
    a = FoscStartPhrasingSlur();
    a.direction.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • parameter

    Gets parameter. Returns 'Beam'.

    '''
    code::
    a = FoscStartPhrasingSlur();
    a.parameter.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • persistent

    Is true.

    '''
    code::
    a = FoscStartPhrasingSlur();
    a.persistent.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • spannerStart

    Is true.

    '''
    code::
    a = FoscStartPhrasingSlur();
    a.spannerStart.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    spannerStart {
        ^true;
    }
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.

    '''
    code::
    a = FoscStartPhrasingSlur();
    a.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • ==

    !!!TODO

    Is true when all initialization values of Abjad value object equal the initialization values of ``argument``.
    '''
    '''
    def __eq__(self, argument) -> bool:
        return StorageFormatManager.compare_objects(self, argument)
    -------------------------------------------------------------------------------------------------------- */
    == {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString

    !!!TODO

    Gets interpreter representation.
    '''
    '''
    def __repr__(self) -> str:
        return StorageFormatManager(self).get_repr_format()
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • hash

    !!!TODO

    Hashes Abjad value object.
    '''
    '''
    def __hash__(self) -> int:
        hash_values = StorageFormatManager(self).get_hash_values()
        try:
            result = hash(hash_values)
        except TypeError:
            raise TypeError(f'unhashable type: {self}')
        return result
    -------------------------------------------------------------------------------------------------------- */
    hash {
        ^this.notYetImplemented(thisMethod);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prAddDirection
    -------------------------------------------------------------------------------------------------------- */
    prAddDirection { |string|
        if (direction.notNil) {
            string = "% %".format(direction, string);
        };
        ^string;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, localTweaks, string;
        bundle = FoscLilypondFormatBundle();
        if (tweaks.notNil) {
            localTweaks = tweaks.prListFormatContributions;
            bundle.after.spannerStarts.addAll(localTweaks);
        };
        string = this.prAddDirection("\\(");
        bundle.after.spannerStarts.add(string);
        ^bundle;
    }
}
