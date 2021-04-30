/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscLilypondFormatBundle


SUMMARY:: Returns a FoscLilypondFormatBundle.


DESCRIPTION:: Transient class created to hold the collection of all format contributions generated on behalf of a single Component.


USAGE::

'''
code::nointerpret
a = FoscLilypondFormatBundle();
a.prGetFormatSpecification;
'''
------------------------------------------------------------------------------------------------------------ */
FoscLilypondFormatBundle : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <absoluteAfter, <absoluteBefore, <after, <before, <closing, <contextSettings, <grobOverrides;
    var <grobReverts, <opening;
    *new {
        ^super.new.init;
    }
    init {
        absoluteBefore = FoscSlotContributions();
        absoluteAfter = FoscSlotContributions();
        before = FoscSlotContributions();
        after = FoscSlotContributions();
        opening = FoscSlotContributions();
        closing = FoscSlotContributions();
        contextSettings = List[];
        grobOverrides = List[];
        grobReverts = List[];
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetFormatSpecification

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.prGetFormatSpecification;
    '''
    -------------------------------------------------------------------------------------------------------- */
    // abjad 3.0 - TODO
    prGetFormatSpecification {
        var slotContributionNames, grobContributionNames, names;
        slotContributionNames = #['absoluteBefore', 'absoluteAfter', 'before', 'after', 'opening',
            'closing', 'right'];
        grobContributionNames = #['contextSettings', 'grobOverrides', 'grobReverts'];
        names = slotContributionNames.select { |name| this.perform(name).hasContributions };
        names = names.add(grobContributionNames.select { |name| this.perform(name).notEmpty });
        ^FoscFormatSpecification(this, storageFormatKwargsNames: names);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • get

    Gets identifier.

    Returns format contributions object or list.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.get('before');
    a.get('grobReverts');
    '''
        -------------------------------------------------------------------------------------------------------- */
    get { |identifier|
        ^this.perform(identifier);
    }
    /* --------------------------------------------------------------------------------------------------------
    • sortOverrides
    -------------------------------------------------------------------------------------------------------- */
    // abjad 3.0
    sortOverrides {
        contextSettings = contextSettings.as(Set).as(Array).sort;
        grobOverrides = grobOverrides.as(Set).as(Array).sort;
        grobReverts = grobReverts.as(Set).as(Array).sort;
    }
    /* --------------------------------------------------------------------------------------------------------
    • update

    Updates format bundle with all format contributions in formatBundle.

    Returns nil.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.update(a.copy);
    '''
    -------------------------------------------------------------------------------------------------------- */
    update { |formatBundle|
        if (formatBundle.respondsTo('prGetLilypondFormatBundle')) {
            formatBundle = formatBundle.prGetLilypondFormatBundle;
        };
        assert(
            formatBundle.isKindOf(FoscLilypondFormatBundle),
            "%:%: argument must be a FoscLilypondFormatBundle: %."
            .format(this.species, thisMethod.name, formatBundle);
        );
        absoluteBefore = absoluteBefore.update(formatBundle.absoluteBefore);
        absoluteAfter = absoluteAfter.update(formatBundle.absoluteAfter);
        before = before.update(formatBundle.before);
        after = after.update(formatBundle.after);
        opening = opening.update(formatBundle.opening);
        closing = closing.update(formatBundle.closing);
        contextSettings.addAll(formatBundle.contextSettings);
        grobOverrides.addAll(formatBundle.grobOverrides);
        grobReverts.addAll(formatBundle.grobReverts);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • absoluteAfter

    Absolute after slot contributions.

    Returns slot contributions object.
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • absoluteBefore

    Absolute after slot contributions.

    Returns slot contributions object.
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • after

    After slot contributions.

    Returns slot contributions object.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.after;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • before

    Before slot contributions.

    Returns slot contributions object.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.before;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • closing

    Closing slot contributions.

    Returns slot contributions object.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.closing;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • contextSettings

    Context setting format contributions.

    Returns array.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.contextSettings;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • grobOverrides

    Grob override format contributions.

    Returns array.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.grobOverrides;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • grobReverts

    Grob revert format contributions.

    Returns array.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.grobReverts;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • opening

    Opening slot contributions.

    Returns slot contributions object.

    '''
    code::
    a = FoscLilypondFormatBundle();
    a.opening;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • right

    Right slot contributions.

    Returns slot contributions object.

    '''
    code::nointerpret
    a = FoscLilypondFormatBundle();
    a.right;
    '''
    -------------------------------------------------------------------------------------------------------- */
}
