/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: slur


SUMMARY:: Returns a slur.


DESCRIPTION:: TODO


USAGE::

'''

• slur (abjad 3.0)

Attaches slur indicators.


• Example 1

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].slur;
a.show;

img:: ![](../img/spanner-slur-1.png)
'''

p = "%/fosc/docs/img/spanner-slur-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Phrasing slurs can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].slur(tweaks: #[['color', 'blue']]);
a.show;

img:: ![](../img/spanner-slur-2.png)
'''

p = "%/fosc/docs/img/spanner-slur-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



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
