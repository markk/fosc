/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: tie


SUMMARY:: Returns a tie.


DESCRIPTION:: Attaches tie indicators.


USAGE::

'''
code::
a = FoscStaff(FoscLeafMaker().(60 ! 4, [1/4]));
a[0..].tie;
a.show;
'''

'''
Ties consecutive chords if all adjacent pairs have at least one pitch in common.

code::
a = FoscStaff(FoscLeafMaker().(#[[60],[60,62],[62]], [1/4]));
a[0..].tie;
a.show;
'''

'''
Same as example 2 but with tie above note on 2nd tie.

code::
a = FoscStaff(FoscLeafMaker().(#[[60],[60,62],[62]], [1/4]));
a[0..1].tie;
a[1..2].tie(direction: 'up');
a.show;
'''

'''
Enharmonics are allowed.

code::
a = FoscStaff(FoscLeafMaker().(#["C4", "B#3", "Dbb4"], [1/4]));
a[0..].tie;
a.show;
'''

'''
Ties can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(60 ! 4, [1/4]));
a[0..].tie(tweaks: #[['color', 'blue']]);
a.show;
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
