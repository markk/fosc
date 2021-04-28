/* ------------------------------------------------------------------------------------------------------------
 (abjad 3.0)
TITLE:: FoscLilypondGrobOverride


SUMMARY:: Returns a FoscLilypondGrobOverride.


DESCRIPTION:: LilyPond grob override.


USAGE::
------------------------------------------------------------------------------------------------------------ */
FoscLilypondGrobOverride : FoscObject {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <lilypondType, <grobName, <isOnce, <isRevert, <propertyPath, <value;
    var <formatLeafChildren=false;
    *new { |lilypondType, grobName='NoteHead', isOnce=false, isRevert=false, propertyPath='color', value='red'|
        if (lilypondType.notNil) { lilypondType = lilypondType.asSymbol };
        assert(grobName.notNil, receiver: this, method: thisMethod);
        grobName = grobName.asSymbol;
        if ([Symbol, String].any { |type| propertyPath.isKindOf(type) }) { propertyPath = [propertyPath] };
        ^super.new.init(lilypondType, grobName, isOnce, isRevert, propertyPath, value);
    }
    init { |argLilypondType, argGrobName, argIsOnce, argIsRevert, argPropertyPath, argValue|
        lilypondType = argLilypondType;
        grobName = argGrobName;
        isOnce = argIsOnce;
        isRevert = argIsRevert;
        propertyPath = argPropertyPath;
        value = argValue;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • grobName

    LilyPond grob override grob name.

    Returns Symbol.

    '''
    code::
    a = FoscLilypondGrobOverride(grobName: 'Glissando');
    a.grobName.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • isOnce

    Is true when grob override is to be applied only once.

    Returns true or false.

    '''
    code::
    a = FoscLilypondGrobOverride("Staff", "NoteHead", isOnce: true);
    a.isOnce.postln;
    '''

    '''
    code::
    a = FoscLilypondGrobOverride(grobName: 'Glissando', isOnce: false);
    a.isOnce.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • isRevert

    Is true when grob override is a grob revert.

    Returns true or false.

    '''
    code::
    a = FoscLilypondGrobOverride("Staff", "NoteHead", isRevert: true);
    a.isRevert.postln;

    '''
    code::
    a = FoscLilypondGrobOverride(grobName: 'Glissando', isRevert: false);
    a.isRevert.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • lilypondType

    Optional Lilypond grob override context name.

    Returns Symbol or nil.

    '''
    code::
    a = FoscLilypondGrobOverride("Staff", "NoteHead", true, false, 'color', 'red');
    a.lilypondType.postln;
    '''

    '''
    code::
    a = FoscLilypondGrobOverride(grobName: 'Glissando');
    a.lilypondType.isNil.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • overrideFormatPieces

    Gets LilyPond grob override \override format pieces.

    Returns array of strings.

    '''
    code::
    a = FoscLilypondGrobOverride(
        lilypondType: "Staff",
        grobName: "TextSpanner",
        isOnce: true,
        propertyPath: #['bound-details', 'left', 'text'],
        value: FoscMarkup("\\bold { over pressure }")
    );
    a.overrideFormatPieces.printAll;

    post::
    \once \override Staff.TextSpanner.bound-details.left.text = \markup { "\bold { over pressure }" }
    '''
    -------------------------------------------------------------------------------------------------------- */
    overrideFormatPieces {
        var result, valuePieces;
        result = [];
        if (isOnce) { result = result.add("\\once") };
        result = result.add("\\override");
        result = result.add(this.prOverridePropertyPathString);
        result = result.add("=");
        valuePieces = FoscScheme.formatEmbeddedSchemeValue(value);
        valuePieces = valuePieces.split("\n");
        result = result.add(valuePieces[0]);
        result = [result.join(" ")];
        result = result.addAll(valuePieces[1..]);
        ^result;
    }
    /* --------------------------------------------------------------------------------------------------------
    • overrideString

    Gets LilyPond grob override \override string.

    Returns string.

    '''
    code::
    a = FoscLilypondGrobOverride(
        grobName: "Glissando",
        propertyPath: 'style',
        value: FoscSchemeSymbol('zigzag')
    );
    a.overrideString.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    overrideString {
        ^this.overrideFormatPieces.join("\n");
    }
    /* --------------------------------------------------------------------------------------------------------
    • propertyPath

    LilyPond grob override property path.

    Returns array of symbols.

    '''
    code::
    a = FoscLilypondGrobOverride(
        lilypondType: "Staff",
        grobName: "TextSpanner",
        isOnce: true,
        propertyPath: #['bound-details', 'left', 'text'],
        value: FoscMarkup("\\bold { over pressure }")
    );
    a.propertyPath[0].postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    • revertFormatPieces

    Gets LilyPond grob override ``\revert`` format pieces.

    Returns array of strings.

    '''
    code::
    a = FoscLilypondGrobOverride(
        grobName: "Glissando",
        propertyPath: 'style',
        value: FoscSchemeSymbol('zigzag')
    );
    a.revertFormatPieces.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    revertFormatPieces {
        var result;
        result = "\\revert %".format(this.prRevertPropertyPathString);
        ^[result];
    }
    /* --------------------------------------------------------------------------------------------------------
    • revertString

    Gets LilyPond grob override ``\revert`` string.

    Returns string.

    '''
    code::
    a = FoscLilypondGrobOverride(
        grobName: "Glissando",
        propertyPath: 'style',
        value: FoscSchemeSymbol('zigzag')
    );
    a.revertString.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    revertString {
        ^this.revertFormatPieces.join("\n");
    }
    /* --------------------------------------------------------------------------------------------------------
    • value

    Value of LilyPond grob override.

    Returns arbitrary object.

    '''
    code::
    a = FoscLilypondGrobOverride(
        lilypondType: "Staff",
        grobName: "TextSpanner",
        isOnce: true,
        propertyPath: #['bound-details', 'left', 'text'],
        value: FoscMarkup("\\bold { over pressure }")
    );
    a.value.postln;
    '''
    -------------------------------------------------------------------------------------------------------- */
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • tweakString

    '''
    code::
    a = FoscLilypondGrobOverride(
        grobName: "Glissando",
        propertyPath: 'style',
        value: FoscSchemeSymbol('zigzag')
    );
    a.tweakString.postln;
    '''

    '''
    code::
    a = FoscLilypondGrobOverride(
        grobName: "RehearsalMark",
        propertyPath: 'color',
        value: 'red'
    );
    a.tweakString.postln;
    '''

    '''
    Lilypond literals are allowed.

    FIXME: ERROR: Message 'name' not understood.

    code::
    a = FoscLilypondGrobOverride(
        grobName: "TextSpan",
        propertyPath: #['bound-details', 'left-broken', 'text'],
        value: FoscLilypondLiteral("\\markup \\upright pont.")
    );
    a.tweakString.postln;
    nointerpret
    '''
    -------------------------------------------------------------------------------------------------------- */
    tweakString { |isDirected=true, grob=false|
        var result, propertyPath, string;

        result =  if (isDirected) { ["- \\tweak"] } { ["\\tweak"] };

        if (grob) {
            propertyPath = [grobName] ++ this.propertyPath;
        } {
            propertyPath = this.propertyPath;
        };

        string = propertyPath.join(".");
        result = result.add(string);

        if (value.isKindOf(FoscLilypondLiteral)) {
            value.name.class.postln;
            assert(
                [String, Symbol].any { |type| value.name.isKindOf(type) },
                receiver: this,
                method: thisMethod
            );
            string = value.name;
        } {
            string = FoscScheme.formatEmbeddedSchemeValue(value);
        };

        result = result.add(string);
        result = result.join(" ");
        ^result;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE METHODS: Special Methods
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • ==

    Is true when 'object' is a FoscLilypondGrobOverride with equivalent instance variable values.

    '''
    FIXME: ERROR: Message 'instVarDict' not understood.

    code::
    a = FoscLilypondGrobOverride("Staff", "NoteHead", true, false, 'color', 'red');
    b = FoscLilypondGrobOverride("Staff", "NoteHead", true, false, 'color', 'red');
    (a == b).postln;
    nointerpret
    '''

    '''
    FIXME: ERROR: Message 'instVarDict' not understood.

    code::
    a = FoscLilypondGrobOverride("Staff", "NoteHead", true, false, 'color', 'red');
    b = FoscLilypondGrobOverride("Voice", "NoteHead", true, false, 'color', 'red');
    (a == b).postln;
    nointerpret
    '''
    -------------------------------------------------------------------------------------------------------- */
    == { |object|
        if (object.isMemberOf(this.species).not) { ^false };
        if (this.instVarDict != object.instVarDict) { ^false };
        ^true;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    • prGetLilypondFormatBundle
    -------------------------------------------------------------------------------------------------------- */
    prGetLilypondFormatBundle { |component|
        var bundle, revertFormat, overrideFormat;
        bundle = FoscLilypondFormatBundle();
        if (isOnce.not) {
            revertFormat = this.revertFormatPieces.join("\n");
            bundle.grobReverts.add(revertFormat);
        };
        if (isRevert.not) {
            overrideFormat = this.overrideFormatPieces.join("\n");
            bundle.grobOverrides.add(overrideFormat);
        };
        ^bundle;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prOverridePropertyPathString
    -------------------------------------------------------------------------------------------------------- */
    prOverridePropertyPathString {
        var parts, path;
        parts = [];
        if (lilypondType.notNil) { parts = parts.add(lilypondType) };
        parts = parts.add(grobName);
        parts = parts.addAll(propertyPath);
        path = parts.join(".");
        ^path;
    }
    /* --------------------------------------------------------------------------------------------------------
    • prRevertPropertyPathString
    -------------------------------------------------------------------------------------------------------- */
    prRevertPropertyPathString {
        var parts, path;
        parts = [];
        if (lilypondType.notNil) { parts = parts.add(lilypondType) };
        parts = parts.add(grobName);
        parts = parts.add(propertyPath[0]);
        path = parts.join(".");
        ^path;
    }
}
