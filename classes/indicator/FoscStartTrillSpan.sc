/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscStartTrillSpan


SUMMARY:: Returns a FoscStartTrillSpan.


DESCRIPTION:: Start a trill span.


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscStartTrillSpan : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <interval, <pitch, <leftBroken=false, <tweaks;
    var <context='Voice', <parameter='TRILL', <persistent=true, <publishStorageFormat=true;
    *new { |interval, pitch, tweaks|
        if (interval.notNil) { interval = FoscInterval(interval) };
        if (pitch.notNil) { pitch = FoscPitch(pitch) };
        ^super.new.init(interval, pitch, tweaks);
    }
    init { |argInterval, argPitch, argTweaks|
        interval = argInterval;
        pitch = argPitch;
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
    a = FoscStartTrillSpan();
    a.context.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • interval

    Gets interval.

    '''
    code::
    a = FoscStartTrillSpan();
    a.interval.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • parameter

    Gets parameter. Returns 'TRILL'.

    '''
    code::
    a = FoscStartTrillSpan();
    a.parameter.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • persistent

    Is true.

    '''
    code::
    a = FoscStartTrillSpan();
    a.persistent.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • pitch

    Gets pitch.

    '''
    code::
    a = FoscStartTrillSpan();
    a.pitch.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • spannerStart

    Is true.

    '''
    code::
    a = FoscStartTrillSpan();
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
    a = FoscStartTrillSpan();
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
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, localTweaks, string, localPitch;
        bundle = FoscLilypondFormatBundle();
        if (tweaks.notNil) {
            localTweaks = tweaks.prListFormatContributions;
            bundle.after.spannerStarts.addAll(localTweaks);
        };
        string = "\\startTrillSpan";
        if (interval.notNil || { pitch.notNil}) {
            bundle.opening.spanners.add("\\pitchedTrill");
            if (pitch.notNil) {
                localPitch = pitch;
            } {
                localPitch = component.writtenPitch + interval;
            };
            string = "% %".format(string, localPitch.str);
        };
        bundle.after.spannerStarts.add(string);
        ^bundle;
    }
}
