/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscTie


SUMMARY:: Returns a FoscTie.


DESCRIPTION:: Attach a tie.


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscTie : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <direction, <rightBroken, <tweaks;
    var <context='Voice', <persistent=true, <publishStorageFormat=true;
    *new { |direction, rightBroken=false, tweaks|
        if (direction.notNil) { direction = direction.toTridirectionalLilypondSymbol };
        ^super.new.initFoscTie(direction, rightBroken, tweaks);
    }
    initFoscTie { |argDirection, argRightBroken, argTweaks|
        direction = argDirection;
        rightBroken = argRightBroken;
        FoscLilypondTweakManager.setTweaks(this, argTweaks);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • context

    Gets context. Returns 'Voice'.

    '''
    code::
    a = FoscTie();
    a.context.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • direction

    Gets direction. Defaults to none.

    Returns up, down or none.

    '''
    Force ties up.

    code::
    a = FoscStaff(FoscLeafMaker().(60 ! 4, [1/8]));
    m = FoscTie(direction: 'up');
    a[0..].attach(m); // FIXME: ERROR: Object:assert
    a.show;
    m.direction;
    nointerpret
    '''

    '''
    Force ties down.

    code::
    a = FoscStaff(FoscLeafMaker().(60 ! 4, [1/8]));
    m = FoscTie(direction: 'down');
    a[0..].attach(m); // FIXME: ERROR: Object:assert
    a.show;
    m.direction;
    nointerpret
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • persistent

    Is true.

    '''
    code::
    a = FoscTie();
    a.persistent.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • rightBroken

    Is true when tie is right-broken.

    '''
    code::
    a = FoscTie();
    a.rightBroken.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • tweaks

    Gets tweaks.

    '''
    code::
    a = FoscTie();
    a.tweaks.postcs;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • ==

    !!!TODO

    Is true when all initialization values of Abjad value object equal the initialization values of ``argument``.
    '''
    '''
    def __eq__(self, argument) -> bool:
        return StorageFormatManager.compare_objects(self, argument)
    -------------------------------------------------------------------------------------------------------- */
    // == {
    //     ^this.notYetImplemented(thisMethod);
    // }
    /* --------------------------------------------------------------------------------------------------------
    • asCompileString

    !!!TODO

    Gets interpreter representation.
    '''
    '''
    def __repr__(self) -> str:
        return StorageFormatManager(self).get_repr_format()
    -------------------------------------------------------------------------------------------------------- */
    asCompileString {
        ^this.notYetImplemented(thisMethod);
    }
    /* --------------------------------------------------------------------------------------------------------
    • hash

    !!!TODO

    Hashes Abjad value object.
    '''
    '''
    def __hash__(self) -> int:
        hash_values = StorageFormatManager(self).get_hash_values()
        try:
            result = hash(hash_values)
        except TypeError:
            raise TypeError(f'unhashable type: {self}')
        return result
    -------------------------------------------------------------------------------------------------------- */
    hash {
        ^this.notYetImplemented(thisMethod);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prAddDirection
    -------------------------------------------------------------------------------------------------------- */
    prAddDirection { |string|
        if (direction.notNil) {
            string = "% %".format(this.direction, string);
        };
        ^string;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, strings, string;
        strings = [];
        bundle = FoscLilypondFormatBundle();
        if (tweaks.notNil) {
            strings = tweaks.prListFormatContributions;
            //!!!TODO if (rightBroken) { strings = this.prTagShow(strings) };
            bundle.after.spannerStarts.addAll(strings);
        };
        string = this.prAddDirection("~");
        strings = [string];
        //!!!TODO if (rightBroken) { strings = this.prTagShow(strings) };
        bundle.after.spannerStarts.addAll(strings);
        ^bundle;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prTagShow
    -------------------------------------------------------------------------------------------------------- */
    prTagShow { |strings|
        ^FoscLilypondFormatManager.tag(
            strings,
            deactivate: true,
            tag: 'SHOW_TO_JOIN_BROKEN_SPANNERS'
        );
    }
}
