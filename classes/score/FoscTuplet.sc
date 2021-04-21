/* ---------------------------------------------------------------------------------------------------------

TITLE:: FoscTuplet


SUMMARY:: Returns a FoscTuplet.


DESCRIPTION:: TODO


USAGE::

'''

• FoscTuplet


• Example 1

A tuplet.

code::
a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
a.show;

img:: ![](../img/score-tuplet-1.png)
'''

p = "%/fosc/docs/img/score-tuplet-1".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 2

A nested tuplet.

code::
a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
b = FoscTuplet(4/7, FoscLeafMaker().(#[67,69], [3/8, 1/16]));
a.insert(1, b);
a.show;

img:: ![](../img/score-tuplet-2.png)
'''

p = "%/fosc/docs/img/score-tuplet-2".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));





• Example 3

Tuplets can be tweaked.

code::
a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
tweak(a).color = 'blue';
a.show;

img:: ![](../img/score-tuplet-3.png)
'''

p = "%/fosc/docs/img/score-tuplet-3".format(Platform.userExtensionDir);
a.writePNG("%.ly".format(p));



'''
------------------------------------------------------------------------------------------------------------ */
FoscTuplet : FoscContainer {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <denominator, <forceFraction, <isHidden, <multiplier, <tweaks;
    *new { |multiplier, components, denominator, forceFraction=false, isHidden=false, tag, tweaks|
        multiplier = multiplier ?? { FoscMultiplier(2, 3) };
        multiplier = FoscMultiplier(multiplier);
        ^super.new(components, tag: tag).initFoscTuplet(multiplier, denominator, forceFraction, isHidden, tweaks);
    }
    initFoscTuplet { |argMultiplier, argDenominator, argForceFraction, argHide, tweaks|
        multiplier = FoscMultiplier(argMultiplier);
        denominator = argDenominator;
        forceFraction = argForceFraction;
        isHidden = argHide;
        FoscLilypondTweakManager.setTweaks(this, tweaks);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *newFromDuration

    Makes tuplet from 'duration' and 'components'.
            
    Returns newly constructed tuplet equal in duration to 'duration'.


    • Example 1

    code::
    m = FoscLeafMaker().(#[60,61,62], [1/8]);
    a = FoscTuplet.newFromDuration(2/8, m);
    a.show;

    img:: ![](../img/score-tuplet-4.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-4".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *newFromDuration { |duration, components|
        var targetDuration, tuplet, contentsDuration, multiplier;
        if (components.isNil || { components.size <= 0 }) {
            ^throw("%::newFromDuration: components can not be empty.".format(this.species));
        };
        targetDuration = FoscDuration(duration);
        tuplet = FoscTuplet(1, components);
        contentsDuration = FoscInspection(tuplet).duration;
        multiplier = targetDuration / contentsDuration;
        tuplet.multiplier_(multiplier);
        ^tuplet;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • *newFromDurationAndRatio

    Makes tuplet from duration and ratio.
 
    Reduces ratio relative to each other.

    Interprets negative ratio values as rests.

    Returns fixed-duration tuplet.


    • Example 1

    Makes augmented tuplet from duration and ratio.

    code::
    a = FoscTuplet.newFromDurationAndRatio(3/16, #[1,1,1,-1,-1]);
    a.show;

    img:: ![](../img/score-tuplet-5.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-5".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    *newFromDurationAndRatio { |duration, ratio, increaseMonotonic=false|
        var basicProlatedDuration, basicWrittenDuration, writtenDurations, leafMaker, notes, denominator;
        var noteDurations, pitches, leafDurations, tuplet;
        
        duration = FoscDuration(duration);
        //!!!TODO: ratio = FoscRatio(ratio);
        basicProlatedDuration = duration / (ratio.abs.sum);
        basicWrittenDuration = basicProlatedDuration.equalOrGreaterAssignable;
        writtenDurations = ratio.collect { |each| each * basicWrittenDuration };
        leafMaker = FoscLeafMaker(increaseMonotonic: increaseMonotonic);

        //!!!TODO: produces overly complex rhythms
        // e.g.: FoscTuplet.newFromDurationAndRatio(3/16, #[1,1,1,1,1]).show;
        // if (writtenDurations.every { |each| each.abs.isAssignable }) {
        //     notes = writtenDurations.collect { |each|
        //         each.str.postln;
        //         if (each > 0) { FoscNote(60, each) } { FoscRest(each.abs) };
        //     };
        // } {
            denominator = duration.denominator;
            noteDurations = ratio.collect { |each| FoscDuration(each, denominator) };
            pitches = noteDurations.collect { |each| if (each > 0) { 60 } { nil } };
            leafDurations = noteDurations.abs;
            notes = leafMaker.(pitches, leafDurations);
        //};

        tuplet = FoscTuplet.newFromDuration(duration, notes);
        tuplet.normalizeMultiplier;
        
        ^tuplet;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: SPECIAL METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • asCompileString
    '''
    -------------------------------------------------------------------------------------------------------- */
    // asCompileString {
    //     ^"FoscTuplet(%, %)".format(multiplier.str, []);
    // }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • storeArgs

    Gets new arguments of tuplet.

    Returns array.


    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4), FoscNote(60, 1/8)]);
    a.storeArgs;
    a.cs; //!!! TODO: make compactRepresentation
    '''
    -------------------------------------------------------------------------------------------------------- */
    storeArgs {
        ^[multiplier, []];
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • denominator

    Gets preferred denominator of tuplet.


    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.denominator; // nil
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • denominator_

    Sets preferred denominator of tuplet.


    • Example 1

    Set preferred denominator.

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.show;

    img:: ![](../img/score-tuplet-6.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-6".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.denominator = 4;
    a.show;

    img:: ![](../img/score-tuplet-7.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-7".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    denominator_ { |denominator|
        assert(
            denominator.isInteger && { denominator > 0 },
            "%:%: denominator must be a positive integer: %."
                .format(this.species, thisMethod.name, denominator);
        );
        this.instVarPut('denominator', denominator);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • forceFraction

    Gets force fraction flag.
    
    Returns true or false.


    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.forceFraction; // false
    code::
    a.show;

    img:: ![](../img/score-tuplet-8.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-8".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • forceFraction_

    Sets force fraction flat.


    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.show;

    img:: ![](../img/score-tuplet-9.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-9".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.forceFraction = true;
    a.show;

    img:: ![](../img/score-tuplet-10.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-10".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    forceFraction_ { |bool|
        if (bool.isKindOf(Boolean)) {
            forceFraction = bool;
        } {
            throw("%:%: argument must be a Boolean: %.".format(this.species, thisMethod.name, bool));
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • impliedProlation

    Gets implied prolation of tuplet.


    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.impliedProlation.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    impliedProlation {
        ^this.multiplier;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • identifier
    !!!TODO
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isHidden

    Is true when tuplet bracket isHiddens.

    Returns true or false.


    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.isHidden; // false
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isHidden_

    Sets isHidden flag.


    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.show;

    img:: ![](../img/score-tuplet-11.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-11".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.isHidden = true;
    a.show;

    img:: ![](../img/score-tuplet-12.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-12".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    isHidden_ { |bool|
        if (bool.isKindOf(Boolean)) {
            isHidden = bool;
        } {
            throw("%:%: argument must be a Boolean: %.".format(this.species, thisMethod.name, bool));
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • multipliedDuration

    Gets multiplied duration of tuplet.

 
    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.multipliedDuration.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    multipliedDuration {
        ^(this.multiplier * this.prGetContentsDuration);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • multiplier

    Gets tuplet multiplier.

    Returns multiplier.


    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.multiplier.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • multiplier_

    Sets tuplet mutliplier.


    • Example 1

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.multiplier_(#[4, 3]);
    a.show;

    img:: ![](../img/score-tuplet-13.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-13".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    multiplier_ { |multiplier| 
        var rational;
        case
        { multiplier.isInteger || { multiplier.isKindOf(FoscFraction) } } {
            rational = FoscMultiplier(multiplier);
        }
        { multiplier.isSequenceableCollection } {
            rational = FoscMultiplier(multiplier); 
        }
        {
            throw("%:%: can't set tuplet multipler from: %."
                .format(this.species, thisMethod.name, multiplier));
        };
        if (rational > 0) {
            multiplier = rational;
        } {
            throw("%:%: multiplier must be positive: %."
                .format(this.species, thisMethod.name, multiplier));   
        };
        this.instVarPut('multiplier', multiplier);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tag

    Gets tag.
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • tweaks

    Gets tweaks.
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • add (append)

    Adds component to tuplet.


    • Example 1

    Add note to tuplet.

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,65], [1/4]));
    a[0..].slur;
    a.show;

    img:: ![](../img/score-tuplet-14.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-14".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.add(FoscNote(64, 1/4));
    a.show;

    img:: ![](../img/score-tuplet-15.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-15".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));





    • Example 2

    Add note to tuplet and preserve tuplet duration.

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,65], [1/4]));
    a[0..].slur;
    a.show;

    img:: ![](../img/score-tuplet-16.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-16".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.add(FoscNote(64, 1/4), preserveDuration: true);
    a.show;

    img:: ![](../img/score-tuplet-17.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-17".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.normalizeMultiplier;
    a.show;

    img:: ![](../img/score-tuplet-18.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-18".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    add { |component, preserveDuration=false|
        var oldDuration, newDuration;
        if (preserveDuration) { oldDuration = this.prGetDuration };
        super.add(component);
        if (preserveDuration) {
            newDuration = this.prGetContentsDuration;
            multiplier = oldDuration / newDuration;
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • addAll (extend)

    Add all components to tuplet.


    • Example 1

    Add three notes to tuplet.

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,65], [1/4]));
    a[0..].slur;
    a.show;

    img:: ![](../img/score-tuplet-19.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-19".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    b = FoscLeafMaker().(#[64,62,64], [1/32,1/32,1/16]);
    a.addAll(b);
    a.show;

    img:: ![](../img/score-tuplet-20.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-20".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));





    • Example 2

    Add three notes to tuplet and preserve tuplet duration.

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,65], [1/4]));
    a[0..].slur;
    a.show;

    img:: ![](../img/score-tuplet-21.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-21".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    b = FoscLeafMaker().(#[64,62,64], [1/32,1/32,1/16]);
    a.addAll(b, preserveDuration: true);
    a.show;

    img:: ![](../img/score-tuplet-22.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-22".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    addAll { |components, preserveDuration=false|
        var oldDuration, newDuration;
        if (preserveDuration) { oldDuration = this.prGetDuration };
        super.addAll(components);
        if (preserveDuration) {
            newDuration = this.prGetContentsDuration;
            multiplier = oldDuration / newDuration;
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • indexOf
    !!!TODO
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • insert
    !!!TODO
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isAugmentation (augmentation)

    Is true when tuplet multiplier is greater than 1.

    Returns true or false.
    

    • Example 1

    Augmented tuplet.

    code::
    a = FoscTuplet(4/3, FoscLeafMaker().(#[60,62,64], [1/4]));
    a.isAugmentation;


    • Example 2

    Diminished tuplet.

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/4]));
    a.isAugmentation;


    • Example 3

    Trivial tuplet.

    code::
    a = FoscTuplet(1/1, FoscLeafMaker().(#[60,62,64], [3/8]));
    a.isAugmentation;
    '''
    -------------------------------------------------------------------------------------------------------- */
    isAugmentation {
        if (multiplier > 0) {
            ^(multiplier > 1);
        } {
            ^false;
        }
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isDiminution (diminution)
     
    Is true when tuplet multiplier is less than 1.

    Returns true or false.

    
    • Example 1

    Augmented tuplet.

    code::
    a = FoscTuplet(4/3, FoscLeafMaker().(#[60,62,64], [1/4]));
    a.isDiminution;


    • Example 2

    Diminished tuplet.

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/4]));
    a.isDiminution;


    • Example 3

    Trivial tuplet.

    code::
    a = FoscTuplet(1/1, FoscLeafMaker().(#[60,62,64], [3/8]));
    a.isDiminution;
    '''
    -------------------------------------------------------------------------------------------------------- */
    isDiminution {
        if (multiplier > 0) {
            ^(multiplier < 1);
        } {
            ^false;
        }
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isTrivial (trivial)

    Is true when tuplet multiplier is equal to 1 and no multipliers attach to any leaves in tuplet.


    • Example 1

    code::
    a = FoscTuplet(1/1, FoscLeafMaker().(#[60,62,64], [3/8]));
    a.isTrivial;


    • Example 2

    Tuplet is not trivial when any leaves have a multiplier.

    code::
    a = FoscTuplet(1/1, FoscLeafMaker().(#[60,62,64], [3/8]));
    a[0].multiplier_(FoscMultiplier(3, 2));
    a.isTrivial;
    '''
    -------------------------------------------------------------------------------------------------------- */
    isTrivial {
        this.doLeaves { |leaf|
            if (leaf.multiplier.notNil) { ^false };
        };
        ^(this.multiplier == 1);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • isTrivializable (trivializable)

    Is true when tuplet is trivializable (can be rewritten with a ratio of 1:1).


    • Example 1

    Trivializable tuplet.

    code::
    a = FoscTuplet(3/4, [FoscNote(60, 1/4), FoscNote(60, 1/4)]);
    a.isTrivializable;


    • Example 2

    Non-trivializable tuplet.

    code::
    a = FoscTuplet(3/5, FoscLeafMaker().(60 ! 5, [1/4]));
    a.isTrivializable;
    '''
    -------------------------------------------------------------------------------------------------------- */
    isTrivializable {
        var duration;
        this.do { |component|
            if (component.isKindOf(FoscTuplet).not) {
                assert(component.isKindOf(FoscLeaf));
                duration = component.writtenDuration * multiplier;
                if (duration.isAssignable.not) { ^false };
            };
            ^true;
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • minimumDenominator_ (set_minimum_denominator)

    Sets preferred denominator of tuplet to at least 'denominator'.


    • Example 1

    Sets preferred denominator of tuplet to at least 8.

    code::
    a = FoscTuplet(3/5, FoscLeafMaker().(#[60,62,64,65,67], [1/4,1/8,1/8,1/4,1/2]));
    a.show;

    img:: ![](../img/score-tuplet-23.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-23".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.minimumDenominator = 8;
    a.show;

    img:: ![](../img/score-tuplet-24.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-24".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    minimumDenominator_ { |denominator|
        var durations, nonreducedFractions;
        assert(
            denominator.isInteger && { denominator > 0 } && { denominator.isPowerOfTwo },
            "%:%: denominator must be non-negative power of two: %"
                .format(this.species, thisMethod.name, denominator);
        );
        durations = [
            this.prGetContentsDuration,
            this.prGetPreprolatedDuration,
            FoscDuration(1, denominator)
        ];
        nonreducedFractions = FoscDuration.durationsToNonreducedFractions(durations);
        denominator = nonreducedFractions[1].numerator;
        this.instVarPut('denominator', denominator);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • normalizeMultiplier

    Normalizes tuplet multiplier.

    
    • Example 1

    code::
    a = FoscTuplet(1/3, FoscLeafMaker().(#[60,62,64], [1/4]));
    a.show;

    img:: ![](../img/score-tuplet-25.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-25".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.normalizeMultiplier;
    a.show;

    img:: ![](../img/score-tuplet-26.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-26".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    normalizeMultiplier {
        var integerExponent, leafMultiplier, oldWrittenDuration, newWrittenDuration, numerator, denominator;
        var localMultiplier;
        integerExponent = this.multiplier.asFloat.log2.asInteger;
        leafMultiplier = FoscMultiplier(2) ** integerExponent;
        this.do { |component|
            if (component.isKindOf(FoscLeaf)) {
                oldWrittenDuration = component.writtenDuration;
                newWrittenDuration = leafMultiplier * oldWrittenDuration;
                component.prSetDuration(newWrittenDuration);
            };
        };
        # numerator, denominator = leafMultiplier.pair;
        localMultiplier = FoscMultiplier(denominator, numerator);
        multiplier = localMultiplier * multiplier; 
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • pop
    !!!TODO
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • remove
    !!!TODO
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • rewriteDots

    Rewrites dots.


    • Example 1

    Rewrites single dots at 3:2 prolation.

    code::
    a = FoscTuplet(1, [FoscNote(60, 3/16), FoscNote(60, 3/16)]);
    a.show;

    img:: ![](../img/score-tuplet-27.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-27".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.rewriteDots;
    a.show;

    img:: ![](../img/score-tuplet-28.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-28".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));





    • Example 2

    Rewrites double dots at 7:2 prolation.

    code::
    a = FoscTuplet(1, [FoscNote(60, 7/32), FoscNote(60, 7/32)]);
    a.show;

    img:: ![](../img/score-tuplet-29.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-29".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.rewriteDots;
    a.show;

    img:: ![](../img/score-tuplet-30.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-30".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));





    • Example 3

    Does nothing when dot counts differ.

    code::
    a = FoscTuplet(1, FoscLeafMaker().(#[60,62,64], [3/16,3/16,1/8]));
    a.show;

    img:: ![](../img/score-tuplet-31.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-31".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.rewriteDots;
    a.show;

    img:: ![](../img/score-tuplet-32.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-32".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));





    • Example 4

    Does nothing leaves carry no dots.

    code::
    a = FoscTuplet(3/2, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.show;

    img:: ![](../img/score-tuplet-33.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-33".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.rewriteDots;
    a.show;

    img:: ![](../img/score-tuplet-34.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-34".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    rewriteDots {
        var dotCounts, dotCount, globalDotCount, dotMultiplier, dotMultiplierReciprocal;
        dotCounts = Set[];
        this.do { |component|
            if (component.isKindOf(FoscTuplet)) { ^this };
            dotCount = component.writtenDuration.dotCount;
            dotCounts.add(dotCount);
        };
        if (1 < dotCounts.size) { ^this };
        assert(dotCounts.size == 1);
        globalDotCount = dotCounts.pop;
        if (globalDotCount == 0) { ^this };
        dotMultiplier = FoscMultiplier.fromDotCount(globalDotCount);
        multiplier = multiplier * dotMultiplier;
        dotMultiplierReciprocal = dotMultiplier.reciprocal;
        this.do { |component|
            component.writtenDuration_(component.writtenDuration * dotMultiplierReciprocal);
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • toggleProlation

    Changes augmented tuplets to diminished; changes diminished tuplets to augmented.
    
    Does not yet work with nested tuplets.


    • Example 1
    
    Changes augmented tuplet to diminished.

    code::
    a = FoscTuplet(4/3, FoscLeafMaker().(#[60,62,64], [1/8]));
    a.show;

    img:: ![](../img/score-tuplet-35.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-35".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.toggleProlation;
    a.show;

    img:: ![](../img/score-tuplet-36.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-36".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));





    • Example 2
    
    Changes diminished tuplet to augmented.

    code::
    a = FoscTuplet(2/3, FoscLeafMaker().(#[60,62,64], [1/4]));
    a.show;

    img:: ![](../img/score-tuplet-37.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-37".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.toggleProlation;
    a.show;

    img:: ![](../img/score-tuplet-38.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-38".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    toggleProlation {
        case
        { this.isDiminution } {
            while { this.isDiminution } {
                multiplier = multiplier * 2;
                FoscIteration(this).leaves.do { |leaf|
                    leaf.writtenDuration_(leaf.writtenDuration / 2);
                };
            };
        }
        { this.isDiminution.not } {
            while { this.isDiminution.not } {
                multiplier = multiplier / 2;
                FoscIteration(this).leaves.do { |leaf|
                    leaf.writtenDuration_(leaf.writtenDuration * 2);
                };
            }; 
        }
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • trivialize

    Trivializes tuplet.


    • Example 1

    code::
    a = FoscTuplet(3/4, [FoscNote(60, 2/4)]);
    a.show;

    img:: ![](../img/score-tuplet-39.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-39".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    a.trivialize;
    a.show;

    img:: ![](../img/score-tuplet-40.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-40".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    trivialize {
        if (this.isTrivializable.not) { ^this };
        this.do { |component|
            case
            { component.isKindOf(FoscTuplet) } {
                component.multiplier_(component.multiplier * multiplier);
            }
            { component.isKindOf(FoscLeaf) } {
                component.writtenDuration_(component.writtenDuration * multiplier);
            }
            {
                throw("%:%: component type error: %".format(this.species, thisMethod.name, component));
            };
        };
        multiplier = FoscMultiplier(1);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatAfterSlot
    '''
    -------------------------------------------------------------------------------------------------------- */
    prFormatAfterSlot { |bundle|
        var result;
        result = [];
        result = result.add(['grob reverts', bundle.grobReverts]);
        result = result.add(['commands', bundle.after.commands]);
        result = result.add(['comments', bundle.after.comments]);
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatBeforeSlot
    '''
    -------------------------------------------------------------------------------------------------------- */
    prFormatBeforeSlot { |bundle|
        var result;
        result = [];
        result = result.add(['comments', bundle.before.comments]);
        result = result.add(['commands', bundle.before.commands]);
        result = result.add(['grobOverrides', bundle.grobOverrides]);
        result = result.add(['contextSettings', bundle.contextSettings]);
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatCloseBracketsSlot
    '''
    -------------------------------------------------------------------------------------------------------- */
    prFormatCloseBracketsSlot {
        var result, strings;
        result = [];
        if (multiplier.notNil) {
            strings = ["}"];
            if (tag.notNil) {
                strings = FoscLilypondFormatManager.tag(strings, tag: tag);
            };
            result = result.add([['selfBrackets', 'close'], strings]);
        };
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatClosingSlot
    '''
    -------------------------------------------------------------------------------------------------------- */
    prFormatClosingSlot { |bundle|
        var result;
        result = [];
        result = result.add(['commands', bundle.closing.commands]);
        result = result.add(['comments', bundle.closing.comments]);
        ^this.prFormatSlotContributionsWithIndent(result);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatLilypondFractionCommandString
    '''
    -------------------------------------------------------------------------------------------------------- */
    prFormatLilypondFractionCommandString {
        if (isHidden) { ^"" };
        if (override(this).tupletNumber.vars.keys.includes('text')) { ^"" };
        if (
            this.isAugmentation
            || { this.prHasPowerOfTwoDenominator.not }
            || { multiplier.denominator == 1 }
            || { this.forceFraction }
        ) {
           ^"\\tweak text #tuplet-number::calc-fraction-text";
        };
        ^"";
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatOpenBracketsSlot
    '''
    -------------------------------------------------------------------------------------------------------- */
    prFormatOpenBracketsSlot {
        var result, contributor, scaleDurationsCommandString, contributions, fractionCommandString;
        var edgeHeightTweakString, strings, timesCommandString;
        result = [];
        if (multiplier > 0) {
            if (isHidden) {
                contributor = [this, 'isHidden'];
                scaleDurationsCommandString = this.prGetScaleDurationsCommandString;
                contributions = [scaleDurationsCommandString];
                result = result.add([contributor, contributions]);
            } {
                contributor = ['selfBrackets', 'open'];
                contributions = [];
                fractionCommandString = this.prFormatLilypondFractionCommandString;
                if (fractionCommandString.notEmpty) {
                    contributions = contributions.add(fractionCommandString);
                };
                edgeHeightTweakString = this.prGetEdgeHeightTweakString;
                if (edgeHeightTweakString.notEmpty) {
                    contributions = contributions.add(edgeHeightTweakString);
                };
                strings = tweak(this).prListFormatContributions(directed: false);
                contributions = contributions.addAll(strings);
                timesCommandString = this.prGetTimesCommandString;
                contributions = contributions.add(timesCommandString);
                if (tag.notNil) {
                    contributions = FoscLilypondFormatManager.tag(contributions, tag: tag);
                };
                result = result.add([contributor, contributions]);
            };
        };
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prFormatOpeningSlot
    '''
    -------------------------------------------------------------------------------------------------------- */
    prFormatOpeningSlot { |bundle|
        var result;
        result = [];
        result = result.add(['comments', bundle.opening.comments]);
        result = result.add(['commands', bundle.opening.commands]);
        ^this.prFormatSlotContributionsWithIndent(result);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetCompactRepresentation

    code::
    a = FoscTuplet(2/3);
    a.prGetCompactRepresentation;

    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4), FoscNote(60, 1/8)]);
    a.prGetCompactRepresentation;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetCompactRepresentation {
        if (components.isEmpty) { ^"{{ % }}".format(multiplier.str) };
        ^"{{ % % }}".format(multiplier.str, this.prGetContentsSummary);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetEdgeHeightTweakString

    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4), FoscNote(60, 1/8)]);
    a.prGetEdgeHeightTweakString;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetEdgeHeightTweakString {
        var duration, denominator;
        duration = this.prGetPreprolatedDuration;
        denominator = duration.denominator;
        if (denominator.isInteger && { denominator > 0 } && { denominator.isPowerOfTwo }) {
            ^"";
        } {
            ^"\\tweak edge-height #'(0.7 . 0)";
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetLilypondFormat
    
    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.prGetLilypondFormat;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormat {
        this.prUpdateNow(indicators: true);
        ^this.prFormatComponent;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetMultiplierFractionString
    
    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.prGetMultiplierFractionString;

    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.denominator = 4;
    a.prGetMultiplierFractionString;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetMultiplierFractionString {
        var inverseMultiplier, nonreducedFraction, numer, denom;
        if (denominator.notNil) {
            inverseMultiplier = this.multiplier.reciprocal;
            nonreducedFraction = FoscNonreducedFraction(inverseMultiplier);
            nonreducedFraction = nonreducedFraction.withDenominator(denominator);
            # denom, numer = nonreducedFraction.pair;
        } {
            # numer, denom = this.multiplier.pair;
        };
        ^"%/%".format(numer, denom);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetPreprolatedDuration
    
    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.prGetPreprolatedDuration.str;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetPreprolatedDuration {
        ^this.multipliedDuration;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetRatioString

    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.prGetRatioString;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetRatioString {
        if (multiplier.notNil) {
            ^"%:%".format(multiplier.denominator, multiplier.numerator);
        } {
            ^nil;
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetScaleDurationsCommandString
    
    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.prGetScaleDurationsCommandString;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetScaleDurationsCommandString {
        var string;
        string = "\\scaleDurations #'(% . %) {";
        string = string.format(multiplier.numerator, multiplier.denominator);
        ^string;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetSummary

    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4), FoscNote(60, 1/8)]);
    a.prGetSummary;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetSummary {
        if (components.size > 0) {
            ^components.collect { |each| each.str }.join(", ");
        } {
            ^"";
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetTimesCommandString

    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.prGetTimesCommandString;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetTimesCommandString {
        ^"\\times % {".format(this.prGetMultiplierFractionString);
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prHasPowerOfTwoDenominator
    
    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.prHasPowerOfTwoDenominator;

    code::
    a = FoscTuplet(3/1, [FoscNote(60, 1/4)]);
    a.prHasPowerOfTwoDenominator;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prHasPowerOfTwoDenominator {
        if (multiplier > 0) {
            ^multiplier.numerator.isPowerOfTwo;
        } {
            ^true;
        };
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prScale

    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.format;

    code::
    a.prScale(1/2);
    a.format;
    '''
    -------------------------------------------------------------------------------------------------------- */
    prScale { |multiplier|
        var newDuration;
        multiplier = FoscMultiplier(multiplier);
        components.do { |component|
            if (component.isKindOf(FoscLeaf)) {
                newDuration = multiplier * component.writtenDuration;
                component.prSetDuration(newDuration);
            };
        };
        this.normalizeMultiplier;
    }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prSimplifyRedundantTuplet

    • Example 1

    code::
    a = FoscSelection(FoscTupletMaker().(2/8 ! 4, [1 ! 7]));
    a = FoscStaff(a);
    mutate(a).partition([4,13,4,7], partitionType: 'sustain');
    a.show;

    img:: ![](../img/score-tuplet-41.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-41".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));




    code::
    // simplify
    FoscIteration(a).components(prototype: FoscTuplet).do { |each| each.prSimplifyRedundantTuplet };
    a.show;

    img:: ![](../img/score-tuplet-42.png)
    '''

    p = "%/fosc/docs/img/score-tuplet-42".format(Platform.userExtensionDir);
    a.writePNG("%.ly".format(p));



    '''
    -------------------------------------------------------------------------------------------------------- */
    // DEPRECATED ??
    // prSimplifyRedundantTuplet {
    //     var leaves, logicalTies, durations, tupletDuration, duration, leaf;

    //     if (this.isRedundant.not) { ^nil };
    //     leaves = [];
    //     logicalTies = FoscSelection(this).byLogicalTie(parentageMask: this);
    //     durations = logicalTies.music.collect { |each| each.duration };
    //     tupletDuration = durations.sum;

    //     logicalTies.do { |logicalTie, i|
    //         duration = durations[i];
    //         if (i == logicalTies.music.lastIndex) {
    //             leaf = logicalTie.tail;
    //         } {
    //             leaf = logicalTie.head;
    //         };
    //         leaf.writtenDuration_(duration);
    //         leaves = leaves.add(leaf);
    //     };

    //     components = leaves;
    //     multiplier = FoscMultiplier(1);
    // }
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prIsRestFilled
    
    code::
    a = FoscTuplet(2/3, [FoscNote(60, 1/4)]);
    a.prIsRestFilled;

    code::
    a = FoscTuplet(2/3, [FoscRest(1/4)]);
    a.prIsRestFilled;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // DEPRECATED ?
    // prIsRestFilled {
    //     iterate(this).byLeaf.do { |leaf| if (leaf.isKindOf(FoscRest).not) { ^false } };
    //     ^true;
    // }
}
