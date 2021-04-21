/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscLilypondLiteral


SUMMARY:: Returns a FoscLilypondLiteral.


DESCRIPTION:: TODO


USAGE::

'''

• FoscLilypondLiteral (abjad 3.0)

LilyPond literal.


• Example 1

Dotted slur.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
m = FoscSlur();
a[0..].attach(m);
l = FoscLilypondLiteral("\\slurDotted");
a[0].attach(l);
a.show;

img:: ![](../img/indicator-lilypond-literal-1.png)
'''

p = "%/fosc/docs/img/indicator-lilypond-literal-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Use the absolute before and absolute after format slots like this.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
l = FoscLilypondLiteral("% before all formatting", formatSlot: 'absoluteBefore');
a[0].attach(l);
l = FoscLilypondLiteral("% after all formatting", formatSlot: 'absoluteAfter');
a[3].attach(l);
a.format;


• Example 3 //!!!TODO: tags not yet implemented

Lilypond literas can be tagged.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
m = FoscSlur();
a[0..].attach(m);
l = FoscLilypondLiteral("\\slurDotted");
a[0].attach(l, tag: 'RED');
a.format;


• Example 4

Multi-line input is allowed.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
l = FoscLilypondLiteral(#[
    "\\stopStaff",
    "\\startStaff",
    "\\once \\override Staff.StaffSymbol.transparent = ##t"
code::
]);
a[2].attach(l);
a.show;

img:: ![](../img/indicator-lilypond-literal-2.png)
'''

p = "%/fosc/docs/img/indicator-lilypond-literal-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscLilypondLiteral : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    classvar <allowableFormatSlots;
    var <canAttachToContainers=true, <formatLeafChildren=false;
    var <string, <directed, <formatSlot, <tweaks;
    *initClass {
        allowableFormatSlots = #[
            'absoluteAfter',
            'absoluteBefore',
            'after',
            'before',
            'closing',
            'opening',
            'right'
        ];
    }
    *new { |string, formatSlot='opening', directed, tweaks|
        assert(
            [Symbol, String, SequenceableCollection].any { |type| string.isKindOf(type) },
            "FoscLilypondLiteral:new: string is not valid: %.".format(string);
        );
        assert(
            allowableFormatSlots.includes(formatSlot),
            "FoscLilypondLiteral:new: format slot is not valid: %.".format(formatSlot);
        );
        assert(
            [FoscLilypondTweakManager, SequenceableCollection, Nil].any { |type| tweaks.isKindOf(type) },
            "FoscLilypondLiteral:new: tweaks must be a FoscLilypondTweakManager or Array: %.".format(tweaks);
        );
        ^super.new.init(string, formatSlot, directed, tweaks);
    }
    init { |argObject, argFormatSlot, argDirected, argTweaks|
        string = argObject;
        formatSlot = argFormatSlot;
        directed = argDirected;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC CLASS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *allowableFormatSlots

    Lists allowable format slots.
    
    code::
    FoscLilypondLiteral.allowableFormatSlots;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • directed

    Is true when literal is directed.


    • Example 1

    Directed literal.

    code::
    l = FoscLilypondLiteral("\\f", 'after', directed: true);
    l.directed;


    • Example 2

    Nondirected literal.

    code::
    l = FoscLilypondLiteral("\\breathe", 'after', directed: false);
    l.directed;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • formatSlot
    
    Gets format slot of LilyPond literal.

    Returns string.

    
    • Example 1
    
    code::
    a = FoscLilypondLiteral(\slurDotted);
    a.formatSlot;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • string

    Gets LilyPond literal string.


    • Example 1

    code::
    l = FoscLilypondLiteral("\\slurDotted");
    l.string;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Gets tweaks.
    
    
    • Example 1

    code::
    a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/4]));
    l = FoscLilypondLiteral("\\f", 'after', directed: true, tweaks: #[['color', 'red']]);
    a[0].attach(l);
    a.show;

    img:: ![](../img/indicator-lilypond-literal-3.png)
    '''

    p = "%/fosc/docs/img/indicator-lilypond-literal-3".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • format

    Formats Lilypond literal.
    '''
    -------------------------------------------------------------------------------------------------------- */
    format {
        ^string.asString;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetFormatPieces
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetFormatPieces {
        if ([Symbol, String].any { |type| string.isKindOf(type) }) { ^[string] };
        assert(string.isSequenceableCollection);
        ^string;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormatBundle
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle {
        var bundle, formatSlot, localTweaks, pieces;
        bundle = FoscLilypondFormatBundle();
        formatSlot = bundle.perform(this.formatSlot);
        if (tweaks.notNil) {
            localTweaks = tweaks.prListFormatContributions(directed: directed);
            formatSlot.commands.addAll(localTweaks);
        };
        pieces = this.prGetFormatPieces;
        formatSlot.commands.addAll(pieces);
        ^bundle;  
    }
}
