/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: hairpin


SUMMARY:: Returns a hairpin.


DESCRIPTION:: Attaches hairpin indicators to leaves in selection.


USAGE::

'''
code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0..].hairpin('p < f');
a.show;
'''

'''
code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0..].hairpin('< f');
a.format;
'''

'''
code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0..].hairpin('f >o !');
a.show;

code::
a.format;
'''

'''
code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0..].hairpin('p > !');
a.show;

code::
a.format;
'''

'''
Hairpin can be tweaked. Note that tweaks effects the hairpin only, and not the dynamics.

code::
a = FoscVoice(FoscLeafMaker().(#[60,62,64,65], 1/4));
a[0..].hairpin('sfz > p', tweaks: #[['color', 'blue']]);
a.show;
'''
------------------------------------------------------------------------------------------------------------ */
+ FoscSelection {
    hairpin { |descriptor, tag, tweaks|
        var indicators, knownShapes, dynamicTrend, dynamic, startDynamic, stopDynamic;
        var leaves, startLeaf, stopLeaf;

        if (descriptor.isKindOf(Symbol)) { descriptor = descriptor.asString };
        indicators = [];
        knownShapes = FoscDynamicTrend.knownShapes;

        if (descriptor.isString) {
            descriptor.delimitByWhiteSpace.do { |string|
                if (knownShapes.includesEqual(string)) {
                    dynamicTrend = FoscDynamicTrend(string);
                    indicators = indicators.add(dynamicTrend);
                } {
                    dynamic = FoscDynamic(string);
                    indicators = indicators.add(dynamic);
                };
            };
        } {
            assert(descriptor.isSequenceableCollection);
            indicators = descriptor;
        };

        case
        { indicators.size == 1 } {
            if (indicators[0].isKindOf(FoscDynamic)) {
                startDynamic = indicators[0];
            } {
                dynamicTrend = indicators[0];
            };
        }
        { indicators.size == 2 } {
            if (indicators[0].isKindOf(FoscDynamic)) {
                # startDynamic, dynamicTrend = indicators;
                //!!!TODO: this is a hack to provide a termination for the dynamic trend
                //!!! stopDynamic = FoscDynamic('niente', command: "\\!");
            } {
                # dynamicTrend, stopDynamic = indicators;
            };
        }
        { indicators.size == 3 } {
            # startDynamic, dynamicTrend, stopDynamic = indicators;
        }
        {
            throw("%:%: bad value for indicators: %.".format(this.species, thisMethod.name, indicators));
        };

        //assert(startDynamic.isKindOf(FoscDynamic));
        leaves = this.leaves;
        startLeaf = leaves.first;
        stopLeaf = leaves.last;

        //!!! not in abjad
        if (dynamicTrend.tweaks.notNil) { tweaks = dynamicTrend.tweaks.addAll(tweaks) };
        FoscLilypondTweakManager.setTweaks(dynamicTrend, tweaks);
        //!!!

        if (startDynamic.notNil) { startLeaf.attach(startDynamic, tag: tag) };
        if (dynamicTrend.notNil) { startLeaf.attach(dynamicTrend, tag: tag) };
        if (stopDynamic.notNil) { stopLeaf.attach(stopDynamic, tag: tag) };
    }
}
