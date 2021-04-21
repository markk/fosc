/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: phrasingSlur


SUMMARY:: Returns a phrasingSlur.


DESCRIPTION:: TODO


USAGE::

'''

• phrasingSlur (abjad 3.0)

Attaches phrasing slur indicators.


• Example 1

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].phrasingSlur;
a.show;

img:: ![](../img/spanner-phrasing-slur-1.png)
'''

p = "%/fosc/docs/img/spanner-phrasing-slur-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

Phrasing slurs can be tweaked.

code::
a = FoscStaff(FoscLeafMaker().(#[60,62,64,65], [1/8]));
a[0..].phrasingSlur(tweaks: #[['color', 'blue']]);
a.show;

img:: ![](../img/spanner-phrasing-slur-2.png)
'''

p = "%/fosc/docs/img/spanner-phrasing-slur-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
+ FoscSelection {
    phrasingSlur { |startPhrasingSlur, stopPhrasingSlur, tag, tweaks|
        var leaves, startLeaf, stopLeaf;
        startPhrasingSlur = startPhrasingSlur ?? { FoscStartPhrasingSlur() };
        stopPhrasingSlur = stopPhrasingSlur ?? { FoscStopPhrasingSlur() };
        leaves = this.leaves;
        startLeaf = leaves.first;
        stopLeaf = leaves.last;
        //!!! not in abjad
        if (startPhrasingSlur.tweaks.notNil) { tweaks = startPhrasingSlur.tweaks.addAll(tweaks) };
        FoscLilypondTweakManager.setTweaks(startPhrasingSlur, tweaks);
        //!!!
        startLeaf.attach(startPhrasingSlur, tag: tag);
        stopLeaf.attach(stopPhrasingSlur, tag: tag);
    }
}
