package com.constant_therapy.util;

public enum KeypadButton {
	SEVEN("7",KeypadButtonCategory.SEVEN)
	, EIGHT("8",KeypadButtonCategory.EIGHT)
	, NINE("9",KeypadButtonCategory.NINE)
	, FOUR("4",KeypadButtonCategory.FOUR)
	, FIVE("5",KeypadButtonCategory.FIVE)
	, SIX("6",KeypadButtonCategory.SIX)
	, ONE("1",KeypadButtonCategory.ONE)
	, TWO("2",KeypadButtonCategory.TWO)
	, THREE("3",KeypadButtonCategory.THREE)
	, LESS("<",KeypadButtonCategory.CLEAR)
	, ZERO("0",KeypadButtonCategory.ZERO)
	, DONE("Done",KeypadButtonCategory.DONE)
;

	CharSequence mText; // Display Text
	KeypadButtonCategory mCategory;
	
	KeypadButton(CharSequence text,KeypadButtonCategory category) {
		mText = text;
		mCategory = category;
	}

	public CharSequence getText() {
	
		return mText;
	}
	
}
