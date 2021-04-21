/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: tie


SUMMARY:: Returns a tie.


DESCRIPTION:: TODO


USAGE::

'''

• tie (abjad 3.0)

Attaches tie indicators.


• Example 1

code::
a = FoscStaff(FoscLeafMaker().(60 ! 4, [1/4]));
a[0..].tie;
a.show;

img:: ![](../img/spanner-tie-1.png)
'''

p = "%/fosc/docs/img/spanner-tie-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Ties consecutive chords if all adjacent pairs have at least one pitch in common.

code::
a = FoscStaff(FoscLeafMaker().(#[[60],[60,62],[62]], [1/4]));
a[0..].tie;
a.show;

img:: ![](../img/spanner-tie-2.png)
'''

p = "%/fosc/docs/img/spanner-tie-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

Same as example 2 but with tie above note on 2nd tie.

code::
a = FoscStaff(FoscLeafMaker().(#[[60],[60,62],[62]], [1/4]));
a[0..1].tie;
a[1..2].tie(direction: 'up');
a.show;

img:: ![](../img/spanner-tie-3.png)
'''

p = "%/fosc/docs/img/spanner-tie-3".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 4

Enharmonics are allowed.

code::
a = FoscStaff(FoscLeafMaker().(#["C4", "B#3", "Dbb4"], [1/4]));
a[0..].tie;
a.show;

img:: ![](../img/spanner-tie-4.png)
'''

p = "%/fosc/docs/img/spanner-tie-4".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 5

Ties can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(60 ! 4, [1/4]));
a[0..].tie(tweaks: #[['color', 'blue']]);
a.show;

img:: ![](../img/spanner-tie-5.png)
'''

p = "%/fosc/docs/img/spanner-tie-5".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
+ FoscSelection {
    tie { |direction, repeat=false, tag, tweaks|
        var inequality, leaves, duration, tie;
        
        leaves = this.leaves;
        //!!!TODO: leaves = this.byLeaf(doNotIterateGraceContainer: true);
        
        if (leaves.size < 2) { throw("Tie selection must contain two or more notes.") };

        leaves.do { |leaf|
            if ([FoscNote, FoscChord].any { |type| leaf.isKindOf(type) }.not) {
                throw("Attempt to tie a non-pitched leaf: %.".format(leaf));
            };
        };

        leaves.doAdjacentPairs { |leafA, leafB|
            duration = leafA.prGetDuration;
            //!!! TODO
            // if inequality(duration):
            //     detach(TieIndicator, current_leaf)
            //     detach(RepeatTie, next_leaf)
            //     repeat_tie = RepeatTie(direction=direction)
            //     attach(repeat_tie, next_leaf, tag=tag)
            // else:
            //     detach(TieIndicator, current_leaf)
            //     detach(RepeatTie, next_leaf)
            //     tie = TieIndicator(direction=direction)
            //     attach(tie, current_leaf, tag=tag)

            leafA.detach(FoscTie);
            //!!! leafB.detach(FoscRepeatTie);
            tie = FoscTie(direction: direction);
            leafA.attach(tie);
            //!!! not in abjad
            if (tie.tweaks.notNil) { tweaks = tie.tweaks.addAll(tweaks) };
            FoscLilypondTweakManager.setTweaks(tie, tweaks);
            //!! 
        };
    }
}
