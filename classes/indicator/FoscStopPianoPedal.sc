/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscStopPianoPedal


SUMMARY:: Returns a FoscStopPianoPedal.


DESCRIPTION:: Lilypond ``\sustainOn``, ``\sostenutoOn``, ``\unaCorda`` commands.


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscStopPianoPedal : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <type, <leak, <tweaks;
    var <context='StaffGroup', <parameter='PEDAL', <persistent=true, <publishStorageFormat=true;
    var <timeOrientation='right';
    *new { |type='sustain', leak=false, tweaks|
        var types;
        if (type.notNil) {
            types = #['sustain', 'sostenuto', 'corda'];
            assert(
                types.includes(type),
                "FoscStopPianoPedal:new: invalid type: '%'. Valid types are: %."
                    .format(type, types.collect { |each| each.cs }.join(", "));
            )
        };
        ^super.new.init(type, leak, tweaks);
    }
    init { |argKind, argLeak, argTweaks|
        type = argKind;
        leak = argLeak;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • context

    Gets context. Returns 'StaffGroup'.

    '''
    code::
    a = FoscStopPianoPedal();
    a.context.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • direction

    Gets direction.

    FIXME: ERROR: Message 'direction' not understood.
    '''
    code::
    a = FoscStopPianoPedal();
    a.direction.postln;
    nointerpret
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • parameter

    Gets parameter. Returns 'Beam'.

    '''
    code::
    a = FoscStopPianoPedal();
    a.parameter.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • persistent

    Is true.

    '''
    code::
    a = FoscStopPianoPedal();
    a.persistent.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • spannerStop

    Is true.

    '''
    code::
    a = FoscStopPianoPedal();
    a.spannerStop.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    spannerStop {
        ^true;
    }
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.

    '''
    code::
    a = FoscStopPianoPedal();
    a.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • type

    Gets type.

    '''
    code::
    a = FoscStopPianoPedal();
    a.type.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, strings, localTweaks, string;
        bundle = FoscLilypondFormatBundle();
        strings = [];
        if (tweaks.notNil) {
            localTweaks = tweaks.prListFormatContributions;
            strings = strings.addAll(tweaks);
        };
        string = switch(type,
            'corda', "\\treCorde",
            'sostenuto', "\\sostenutoOff",
            'sustain', "\\sustainOff"
        );
        strings = strings.add(string);
        if (leak) {
            strings = strings.insert(0, "<> %".format(string));
            bundle.after.leaks.addAll(strings);
        } {
            bundle.after.spannerStops.addAll(strings);
        };
        ^bundle;
    }
}
