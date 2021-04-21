/* ------------------------------------------------------------------------------------------------------------

TITLE:: FoscDrumNoteHead


SUMMARY:: Returns a FoscDrumNoteHead.


DESCRIPTION:: TODO


USAGE::

'''

• FoscDrumNoteHead

A drum note-head.

>>> note_head = scoretools.DrumNoteHead('snare')
>>> note_head
DrumNoteHead('snare')

code::
a = FoscDrumNoteHead('snare');
a.pitch;

code::
FoscDrumNoteHead('foo');
'''
------------------------------------------------------------------------------------------------------------ */
FoscDrumNoteHead : FoscNoteHead {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INIT
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	*new { |writtenPitch='snare', client, isCautionary, isForced, isParenthesized, tweaks|
		var drumPitch;
        if (LilypondDrums.includes(writtenPitch.asSymbol).not) {
			throw("%::new: name is not in LilypondDrums: %.".format(this.species, writtenPitch));
		};
        drumPitch = LilypondDrums.at(writtenPitch.asSymbol);
		^super.new(drumPitch, client, isCautionary, isForced, isParenthesized, tweaks);
	}
}
