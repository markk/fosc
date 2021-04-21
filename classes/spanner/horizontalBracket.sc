/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: horizontalBracket


SUMMARY:: Returns a horizontalBracket.


DESCRIPTION:: TODO


USAGE::

'''

• horizontalBracket (abjad 3.0)

Attaches group indicators.

textAlign: [lilypond: parent-alignment-X]: Specify on which point of the parent the object is aligned. The value -1 means aligned on parent’s left edge, 0 on center, and 1 right edge, in X direction. Other numerical values may also be specified - the unit is half the parent’s width. If unset, the value from self-alignment-X property will be used.



• Example 1

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a.consistsCommands.add('Horizontal_bracket_engraver');
a[0..].horizontalBracket;
a.show;
a.format;


• Example 2

Horizontal bracket can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a.consistsCommands.add('Horizontal_bracket_engraver');
t = #['bracket-flare', [0,0], 'color', 'red', 'direction', 'up', 'staff-padding', 3];
a[0..].horizontalBracket(tweaks: t);
a.show;

img:: ![](../img/spanner-horizontal-bracket-1.png)
'''

p = "%/fosc/docs/img/spanner-horizontal-bracket-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

Partition selection by sizes, bracket each new selection, add text annotation.

code::
a = FoscStaff(FoscLeafMaker().((60..75), [1/32]));
t = #[['bracket-flare', [0,0]], ['direction', 'up'], ['staff-padding', 0]];

code::
a[0..].partitionBySizes(#[3,4,6,3]).do { |sel, i|
    sel.horizontalBracket(text: FoscMarkup(sel.size.asString).fontSize(-2), textAlign: -0.8, tweaks: t);
};

code::
a.show;

img:: ![](../img/spanner-horizontal-bracket-2.png)
'''

p = "%/fosc/docs/img/spanner-horizontal-bracket-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
+ FoscSelection {
    horizontalBracket { |startGroup, stopGroup, text, textAlign=0, tweaks|
        var leaves, startLeaf, stopLeaf, tweak;

        startGroup = startGroup ?? { FoscStartGroup() };
        stopGroup = stopGroup ?? { FoscStopGroup() };
        leaves = this.leaves;
        startLeaf = leaves.first;
        stopLeaf = leaves.last;

        if (text.notNil) {
            if (tweaks.isNil) {
                tweaks = [];
            } {
                tweaks = tweaks.copy;
            };

            text = FoscMarkup(text);

            tweaks = tweaks.addAll([
                [FoscLilypondLiteral("HorizontalBracketText.text"), text],
                [FoscLilypondLiteral("HorizontalBracketText.parent-alignment-X"), textAlign]
            ]);
        };

        if (startGroup.tweaks.notNil) { tweaks = startGroup.tweaks.addAll(tweaks) };
        FoscLilypondTweakManager.setTweaks(startGroup, tweaks);
        
        startLeaf.attach(startGroup);
        stopLeaf.attach(stopGroup);
    }
}
