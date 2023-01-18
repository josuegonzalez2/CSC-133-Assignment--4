package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;

public class CmdButton extends Button{
	public CmdButton(Command cmd)
	{
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBgColor(ColorUtil.BLUE);
		this.getAllStyles().setFgColor(ColorUtil.WHITE);
		this.getAllStyles().setPadding(Component.TOP, 5);
		this.getAllStyles().setPadding(Component.BOTTOM, 5);
	}
}