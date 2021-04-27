/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscRepeat


SUMMARY:: Returns a FoscRepeat.


DESCRIPTION:: TODO


USAGE::

'''

• FoscRepeat (abjad 3.0)

Repeat


• Example 1

Volta repeat.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
m = FoscRepeat();
a.attach(m);
a.show;

img:: ![](../img/indicator-repeat-1.png)
'''

p = "%/fosc/docs/img/indicator-repeat-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Unfold repeat.

code::
a = FoscContainer(FoscLeafMaker().(#[60,62,64,65], [1/4]));
m = FoscRepeat(repeatCount: 3, repeatType: 'unfold');
a.attach(m);
b = FoscScore([a]);
b.show;

img:: ![](../img/indicator-repeat-2.png)
'''

p = "%/fosc/docs/img/indicator-repeat-2".format(Platform.userExtensionDir);
b.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscRepeat : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <repeatCount, <repeatType;
    var <canAttachToContainers=true, <context='Score', <formatLeafChildren=false, <formatSlot='before';
    *new { |repeatCount=2, repeatType='volta'|
        assert(repeatCount.isInteger && repeatCount > 1);
        assert(#['volta', 'unfold'].includes(repeatType));
        ^super.new.init(repeatCount, repeatType);
    }
    init { |argRepeatCount, argRepeatType|
        repeatCount = argRepeatCount;
        repeatType = argRepeatType;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • context

    Gets context.


    • Example 1

    code::
    m = FoscRepeat();
    m.context.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • repeatCount

    Gets repeat count of repeat.


    • Example 1

    code::
    m = FoscRepeat();
    m.repeatCount;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • repeatType

    Gets repeat type of repeat.


    • Example 1

    code::
    m = FoscRepeat();
    m.repeatType.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Tweaks are not implemented on repeat.

    The LilyPond \repeat command refuses tweaks.

    Override the LilyPond 'BarLine' grob instead.
    '''
    -------------------------------------------------------------------------------------------------------- */
    tweaks {
        // pass
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • str

    Gets string representation of repeat


    • Example 1

    code::
    m = FoscRepeat();
    m.str.cs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^"\\repeat % %".format(repeatType, repeatCount);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormat
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        ^this.str;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormatBundle
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle;
        bundle = FoscLilypondFormatBundle();
        bundle.before.commands.add(this.prGetLilypondFormat);
        ^bundle;
    }
}
