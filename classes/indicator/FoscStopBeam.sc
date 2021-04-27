/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscStopBeam


SUMMARY:: Returns a FoscStopBeam.


DESCRIPTION:: TODO


USAGE::

'''

• FoscStopBeam (abjad 3.0)
'''
------------------------------------------------------------------------------------------------------------ */
FoscStopBeam : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <leak;
    var <context='Voice', <parameter='BEAM', <persistent=true, <publishStorageFormat=true;
    var <timeOrientation='right';
    *new { |leak=false|
        // assert(leak.isKindOf(Boolean));
        ^super.new.init(leak);
    }
    init { |argLeak|
        leak = argLeak;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • context

    Gets context. Returns 'Voice'.


    • Example 1

    code::
    a = FoscStopBeam();
    a.context;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • leak

    Is true when stop beam leaks LilyPond '<>'' empty chord


    • Example 1

    code::
    a = FoscStopBeam();
    a.leak;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • parameter

    Gets parameter. Returns 'Beam'.


    • Example 1

    code::
    a = FoscStopBeam();
    a.parameter;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • persistent

    Is true.


    • Example 1

    code::
    a = FoscStopBeam();
    a.persistent;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • spannerStop

    Is true.


    • Example 1

    code::
    a = FoscStopBeam();
    a.spannerStop;
    '''
    -------------------------------------------------------------------------------------------------------- */
    spannerStop {
        ^true;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • asCompileString

    !!!TODO

    Gets interpreter representation.

    def __repr__(self) -> str:
        return StorageFormatManager(self).get_repr_format()
    '''
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^this.notYetImplemented(thisMethod);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormatBundle
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, localTweaks, string;
        bundle = FoscLilypondFormatBundle();
        string = "]";
        if (leak) {
            string = "<> %".format(string);
            bundle.after.leaks.add(string);
        } {
            bundle.after.spannerStarts.add(string);
        };
        ^bundle;
    }
}
