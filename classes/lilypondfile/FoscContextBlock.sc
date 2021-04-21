/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscContextBlock


SUMMARY:: Returns a FoscContextBlock.


DESCRIPTION:: TODO


USAGE::

'''

• FoscContextBlock

!!!TODO: needs to be updated

A LilyPond file \context block.


code::
a = FoscContextBlock(sourceLilypondType: 'Staff', name: 'FluteStaff', type: 'Engraver_group', alias: 'Staff');

code::
a.removeCommands.add('Forbid_line_break_engraver');
a.consistsCommands.add('Horizontal_bracket_engraver');
a.acceptsCommands.add('FluteUpperVoice');
a.acceptsCommands.add('FluteLowerVoice');
a.items.add("\\accidentalStyle dodecaphonic");
override(a).beam.positions = #[4,4];
set(a).autoBeaming = false;
set(a).tupletFullLength = true;
set(a).proportionalNotationDuration = FoscSchemeMoment(#[1,24]);
a.format;
'''
------------------------------------------------------------------------------------------------------------ */
FoscContextBlock : FoscBlock {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    var <sourceLilypondType, <type, <alias, <acceptsCommands, <consistsCommands, <removeCommands;
    var <overrides, <lilypondSettingNameManager; //!!! not in abjad, but needed for 'override' and 'set'
    *new { |sourceLilypondType, name, type, alias|
        if (sourceLilypondType.notNil) { sourceLilypondType = sourceLilypondType.asSymbol };
        ^super.new(name: 'context').initFoscContextBlock(sourceLilypondType, name, type, alias);
    }
    initFoscContextBlock { |argSourcelilypondType, argName, argType, argAlias|
        sourceLilypondType = argSourcelilypondType;
        name = argName;
        type = argType;
        alias = argAlias;
        acceptsCommands = List[];
        consistsCommands = List[];
        removeCommands = List[];
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • prGetFormatPieces
    '''
    -------------------------------------------------------------------------------------------------------- */
    prGetFormatPieces {
        var indent, result, string, manager, pieces, overrides, settingContributions, settingContribution;
        var key, val;
        
        indent = FoscLilypondFormatManager.indent;
        result = [];
        string = "% {".format(escapedName);
        result = result.add(string);
        manager = FoscLilypondFormatManager;

        if (sourceLilypondType.notNil) {
            string = indent ++ "\\" ++ sourceLilypondType;
            result = result.add(string);
        };
        if (name.notNil) {
            string = indent ++ "\\name %".format(name);
            result = result.add(string);
        };
        if (type.notNil) {
            string = indent ++ "\\type %".format(type);
            result = result.add(string);
        };
        if (alias.notNil) {
            string = indent ++ "\\alias %".format(alias);
            result = result.add(string);
        };
        removeCommands.do { |statement|
            string = indent ++ "\\remove %".format(statement);
            result = result.add(string);
        };
        consistsCommands.do { |statement|
            string = indent ++ "\\consists %".format(statement);
            result = result.add(string);
        };
        acceptsCommands.do { |statement|
            string = indent ++ "\\accepts %".format(statement);
            result = result.add(string);
        };

        overrides = override(this).prListFormatContributions('override');
        overrides.do { |statement|
            string = indent ++ statement.format;
            result = result.add(string);
        };

        settingContributions = [];
        set(this).prAttributeTuples.do { |each|
            # key, val = each;
            key = key.asString.strip("_");
            settingContribution = manager.formatLilypondContextSettingInWithBlock(key, val);
            settingContributions = settingContributions.add(settingContribution);
        };
        settingContributions.sort.do { |settingContribution|
            string = indent ++ settingContribution;
            result = result.add(string);
        };

        items.do { |item|
            case
            { item.isString } {
                string = indent ++ item;
                result = result.add(string);
            }
            { item.respondsTo('prGetFormatPieces') } {
                pieces = item.prGetFormatPieces;
                pieces = pieces.collect { |piece| indent ++ piece };
                result = result.addAll(pieces);
            };
        };
        result = result.add("}");
        ^result;

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC INSTANCE PROPERTIES
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* --------------------------------------------------------------------------------------------------------
    '''
    • acceptsCommands
    
    Gets arguments of LilyPond \accepts commands.

    Returns list.

    code::
    a.acceptsCommands;
    >> ['FluteUpperVoice', 'FluteLowerVoice']
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • alias

    Gets and sets argument of LilyPond \alias command.

    Returns string or none.

    code::
    a.alias;
    >> 'Staff'
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • consistsCommands

    Gets arguments of LilyPond \consists commands.

    Returns list.
    
    code::
    a.consistsCommands;
    >> ['Horizontal_bracket_engraver']
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • items

    Gets items in context block.

    Returns list.
        
    code::
    a.items;
    >> ['\accidentalStyle dodecaphonic']
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • name
    
    Gets and sets argument of LilyPond \name command.

    Returns string or none.
    
    code::
    a.name;
    >> 'FluteStaff'
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • removeCommands
    
    Gets arguments of LilyPond \remove commands.
        
    Returns list.
        
    code::
    a.removeCommands;
    >> ['Horizontal_bracket_engraver']
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • sourceLilypondType
    
    Gets and sets source context name.

    Returns string or none.
    
    code::
    a.sourceLilypondType;
    >> 'Staff'
    '''
    -------------------------------------------------------------------------------------------------------- */
    /* --------------------------------------------------------------------------------------------------------
    '''
    • type
    
    Gets and sets argument of LilyPond \type command.

    Returns string or none.
    
    code::
    a.type;
    >> 'Engraver_group'
    '''
    -------------------------------------------------------------------------------------------------------- */
}
