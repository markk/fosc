/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscStopTrillSpan


SUMMARY:: Returns a FoscStopTrillSpan.


DESCRIPTION:: End a trill.


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscStopTrillSpan : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <leak, <rightBroken;
    var <context='Voice', <parameter='TRILL', <persistent=true, <publishStorageFormat=true;
    var <timeOrientation='right';
    *new { |leak=false, rightBroken=false|
        // assert(leak.isKindOf(Boolean));
        // assert(rightBroken.isKindOf(Boolean));
        ^super.new.init(leak, rightBroken);
    }
    init { |argLeak, argRightBroken|
        leak = argLeak;
        rightBroken = argRightBroken;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • context

    Gets context. Returns 'Voice'.

    '''
    code::
    a = FoscStopTrillSpan();
    a.context.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • leak

    Is true when stop beam leaks LilyPond '<>'' empty chord

    '''
    code::
    a = FoscStopTrillSpan();
    a.leak.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • parameter

    Gets parameter. Returns 'TRILL'.

    '''
    code::
    a = FoscStopTrillSpan();
    a.parameter.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • persistent

    Is true.

    '''
    code::
    a = FoscStopTrillSpan();
    a.persistent.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • rightBroken

    Is true when stop trill spanner is right-broken.

    '''
    code::
    a = FoscStopTrillSpan();
    a.rightBroken.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • spannerStop

    Is true.

    '''
    code::
    a = FoscStopTrillSpan();
    a.spannerStop.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    spannerStop {
        ^true;
    }
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

    bundle = LilyPondFormatBundle()
    string = r'\stopTrillSpan'
    if self.right_broken:
        string = self._tag_hide([string])[0]
    if self.leak:
        string = f'<> {string}'
        bundle.after.leaks.append(string)
    else:
        bundle.after.spanner_stops.append(string)
    return bundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, localTweaks, string;
        bundle = FoscLilypondFormatBundle();
        string = "\\stopTrillSpan";
        if (rightBroken) {
            string = this.prTagHide([string])[0];
        };
        if (leak) {
            string = "<> %".format(string);
            bundle.after.leaks.add(string);
        } {
            bundle.after.spannerStarts.add(string);
        };
        ^bundle;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prTagHide
    -------------------------------------------------------------------------------------------------------- */
    prTagHide { |strings|
        ^this.notYetImplemented(thisMethod);
        // var abjadTags;
        // abjadTags = FoscTags();
        // ^FoscLilypondFormatManager.tag(
        //     strings,
        //     deactivate: false,
        //     tag: abjadTags.'HIDE_TO_JOIN_BROKEN_SPANNERS'
        // );
    }
}
