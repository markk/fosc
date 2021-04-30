/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: slur


SUMMARY:: Returns a slur.


DESCRIPTION:: Attaches slur indicators.


USAGE::

'''
code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].slur;
a.show;
'''

'''
Phrasing slurs can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].slur(tweaks: #[['color', 'blue']]);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
+ FoscSelection {
    slur { |startSlur, stopSlur, tag, tweaks|
        var leaves, startLeaf, stopLeaf;
        startSlur = startSlur ?? { FoscStartSlur() };
        stopSlur = stopSlur ?? { FoscStopSlur() };
        leaves = this.leaves;
        startLeaf = leaves.first;
        stopLeaf = leaves.last;
        //!!! not in abjad
        if (startSlur.tweaks.notNil) { tweaks = startSlur.tweaks.addAll(tweaks) };
        FoscLilypondTweakManager.setTweaks(startSlur, tweaks);
        //!!!
        startLeaf.attach(startSlur, tag: tag);
        stopLeaf.attach(stopSlur, tag: tag);
    }
}
