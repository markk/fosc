/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscRehearsalMark


SUMMARY:: Returns a FoscRehearsalMark.


DESCRIPTION:: Rehearsal mark.


USAGE::

'''
Initialize from number.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
a = FoscScore([a]);
m = FoscRehearsalMark(number: 1);
a.leafAt(0).attach(m);
set(a).markFormatter = FoscScheme('format-mark-box-alphabet');
a.show;
'''

'''
Initialize from markup.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
a = FoscScore([a]);
m = FoscRehearsalMark(markup: 'A1');
a.leafAt(0).attach(m);
a.show;
'''

'''
Reharsal mark can be tweaked when markup is not nil.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
a = FoscScore([a]);
m = FoscRehearsalMark(markup: "A1", tweaks: #['color', 'blue']);
a.leafAt(0).attach(m);
a.show;
'''

'''
Tweaks have no effect when markup is nil.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
a = FoscScore([a]);
m = FoscRehearsalMark(number: 1, tweaks: #['color', 'blue']);
a.leafAt(0).attach(m);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
FoscRehearsalMark : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <number, <markup, <tweaks;
    var <context='Score';
    *new { |number, markup, tweaks|
        assert([FoscMarkup, String, Symbol, Nil].any { |type| markup.isKindOf(type) });
        if (markup.notNil) { markup = FoscMarkup(markup) };
        assert([Integer, Nil].any { |type| number.isKindOf(type) });
        ^super.new.init(number, markup, tweaks);
    }
    init { |argNumber, argMarkup, argTweaks|
        number = argNumber;
        markup = argMarkup;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • context

    Gets context of rehearsal mark. Returns 'Score'.

    '''
    code::
    m = FoscRehearsalMark(number: 1);
    m.context.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • markup

    Gets rehearsal mark markup.

    '''
    code::
    m = FoscRehearsalMark(markup: FoscMarkup("A1"));
    m.markup.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • number

    Gets rehearsal mark number.

    '''
    code::
    m = FoscRehearsalMark(number: 1);
    m.number.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.

    '''
    code::
    m = FoscRehearsalMark(number: 1, tweaks: #[['color', 'red']]);
    m.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • copy

    Copies rehearsal mark.
    -------------------------------------------------------------------------------------------------------- */
    copy {
        var new;
        new = this.species.new(this);
        FoscLilypondTweakManager.setTweaks(new, tweaks.copy);
        ^new;
    }
    /* --------------------------------------------------------------------------------------------------------
    • str

    Gets string representation of rehearsal mark.

    '''
    code::
    m = FoscRehearsalMark(number: 1);
    m.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    str {
        ^this.prGetLilypondFormat;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormat
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        var result;
        case
        { markup.notNil } {
            result = "\\mark %".format(markup.str);
        }
        { number.notNil } {
            result = "\\mark #%".format(number);
        }
        {
            result = "\\mark \\default";
        };
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, localTweaks;
        bundle = FoscLilypondFormatBundle();
        if (tweaks.notNil) {
            localTweaks = tweaks.prListFormatContributions(directed: false);
            bundle.opening.commands.addAll(localTweaks);
        };
        bundle.opening.commands.add(this.prGetLilypondFormat);
        ^bundle;
    }
}
