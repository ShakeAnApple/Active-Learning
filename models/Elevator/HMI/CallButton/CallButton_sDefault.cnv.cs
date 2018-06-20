﻿/*
 * Created by nxtSTUDIO.
 * User: Andrei
 * Date: 6/3/2016
 * Time: 3:38 PM
 * 
 */

using System;
using System.Drawing;
using NxtControl.GuiFramework;

namespace HMI.Main.Symbols.CallButton
{
	/// <summary>
	/// Description of sDefault.
	/// </summary>
	public partial class sDefault : NxtControl.GuiFramework.HMISymbol
	{
		public sDefault()
		{
			//
			// The InitializeComponent() call is required for Windows Forms designer support.
			//
			InitializeComponent();
      this.REQ_Fired += REQ_Fired_EventHandler;
      ledON.Visible = false;
		}
		
		void setButton(bool value)
		{
		    ledON.Visible = value;
		    this.FireEvent_CHG(value);
		}
		
		void REQ_Fired_EventHandler(object sender, HMI.Main.Symbols.CallButton.REQEventArgs ea)
		{
		  if ((bool)ea.ResetButton)
		    setButton(false);
		  if ((bool)ea.ForcedTurnOn)
		    setButton(true);
		}
		
		void Ellipse3Click(object sender, EventArgs e)
		{
		    setButton(true);
		}
	}
}
