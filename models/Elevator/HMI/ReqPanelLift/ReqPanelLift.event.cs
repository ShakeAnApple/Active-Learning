/*
 * Created by nxtSTUDIO.
 * User: Andrei
 * Date: 6/3/2016
 * Time: 12:05 PM
 * 
 */
using System;
using NxtControl.GuiFramework;
using NxtControl.Services;

#region Definitions;
#region #ReqPanelLift_HMI;

namespace HMI.Main.Symbols.ReqPanelLift
{

  public class REQ0EventArgs : System.EventArgs
  {
    IHMIAccessorService accessorService;
    int channelId;
    int cookie; 
    int eventIndex;

    public REQ0EventArgs(int channelId, int cookie, int eventIndex)
    {
      this.accessorService = (IHMIAccessorService)ServiceProvider.GetService(typeof(IHMIAccessorService));
      this.channelId = channelId;
      this.cookie = cookie;
      this.eventIndex = eventIndex;
    }
    public bool Get_Button0Reset(ref System.Boolean value)
    {
      if (accessorService == null)
        return false;
      bool var = false;
      bool ret = accessorService.GetBoolValue(channelId, cookie, eventIndex, true,0, ref var);
      if (ret) value = (System.Boolean) var;
      return ret;
    }

    public System.Boolean? Button0Reset
    { get {
      if (accessorService == null)
        return null;
      bool var = false;
      bool ret = accessorService.GetBoolValue(channelId, cookie, eventIndex, true,0, ref var);
      if (!ret) return null;
      return (System.Boolean) var;
    }  }


  }

  public class REQ1EventArgs : System.EventArgs
  {
    IHMIAccessorService accessorService;
    int channelId;
    int cookie; 
    int eventIndex;

    public REQ1EventArgs(int channelId, int cookie, int eventIndex)
    {
      this.accessorService = (IHMIAccessorService)ServiceProvider.GetService(typeof(IHMIAccessorService));
      this.channelId = channelId;
      this.cookie = cookie;
      this.eventIndex = eventIndex;
    }
    public bool Get_Button1Reset(ref System.Boolean value)
    {
      if (accessorService == null)
        return false;
      bool var = false;
      bool ret = accessorService.GetBoolValue(channelId, cookie, eventIndex, true,0, ref var);
      if (ret) value = (System.Boolean) var;
      return ret;
    }

    public System.Boolean? Button1Reset
    { get {
      if (accessorService == null)
        return null;
      bool var = false;
      bool ret = accessorService.GetBoolValue(channelId, cookie, eventIndex, true,0, ref var);
      if (!ret) return null;
      return (System.Boolean) var;
    }  }


  }

  public class REQ2EventArgs : System.EventArgs
  {
    IHMIAccessorService accessorService;
    int channelId;
    int cookie; 
    int eventIndex;

    public REQ2EventArgs(int channelId, int cookie, int eventIndex)
    {
      this.accessorService = (IHMIAccessorService)ServiceProvider.GetService(typeof(IHMIAccessorService));
      this.channelId = channelId;
      this.cookie = cookie;
      this.eventIndex = eventIndex;
    }
    public bool Get_Button2Reset(ref System.Boolean value)
    {
      if (accessorService == null)
        return false;
      bool var = false;
      bool ret = accessorService.GetBoolValue(channelId, cookie, eventIndex, true,0, ref var);
      if (ret) value = (System.Boolean) var;
      return ret;
    }

    public System.Boolean? Button2Reset
    { get {
      if (accessorService == null)
        return null;
      bool var = false;
      bool ret = accessorService.GetBoolValue(channelId, cookie, eventIndex, true,0, ref var);
      if (!ret) return null;
      return (System.Boolean) var;
    }  }


  }

}

namespace HMI.Main.Symbols.ReqPanelLift
{

  public class CHGEventArgs : System.EventArgs
  {
    public CHGEventArgs()
    {
    }
    private System.Boolean? ButtonFor0_field = null;
    public System.Boolean? ButtonFor0
    {
       get { return ButtonFor0_field; }
       set { ButtonFor0_field = value; }
    }
    private System.Boolean? ButtonFor1_field = null;
    public System.Boolean? ButtonFor1
    {
       get { return ButtonFor1_field; }
       set { ButtonFor1_field = value; }
    }
    private System.Boolean? ButtonFor2_field = null;
    public System.Boolean? ButtonFor2
    {
       get { return ButtonFor2_field; }
       set { ButtonFor2_field = value; }
    }

  }

}

namespace HMI.Main.Symbols.ReqPanelLift
{
  partial class sDefault
  {

    private event EventHandler<HMI.Main.Symbols.ReqPanelLift.REQ0EventArgs> REQ0_Fired;

    private event EventHandler<HMI.Main.Symbols.ReqPanelLift.REQ1EventArgs> REQ1_Fired;

    private event EventHandler<HMI.Main.Symbols.ReqPanelLift.REQ2EventArgs> REQ2_Fired;

    protected override void OnEndInit()
    {
      if (REQ0_Fired != null)
        AttachEventInput(0);
      if (REQ1_Fired != null)
        AttachEventInput(1);
      if (REQ2_Fired != null)
        AttachEventInput(2);

    }

    protected override void FireEventCallback(int channelId, int cookie, int eventIndex)
    {
      switch(eventIndex)
      {
        default:
          break;
        case 0:
          if (REQ0_Fired != null)
          {
            try
            {
              REQ0_Fired(this, new HMI.Main.Symbols.ReqPanelLift.REQ0EventArgs(channelId, cookie, eventIndex));
            }
            catch (System.Exception e)
            {
              NxtControl.Services.LoggingService.ErrorFormatted(@"In Event Callback for event:'{0}' Type:'{1}' CAT:'{2}' came exception:{3}
stack Trace:
{4}","REQ0_Fired", this.GetType().Name, this.CATName, e.Message, e.StackTrace);
            }
          }
        break; 
        case 1:
          if (REQ1_Fired != null)
          {
            try
            {
              REQ1_Fired(this, new HMI.Main.Symbols.ReqPanelLift.REQ1EventArgs(channelId, cookie, eventIndex));
            }
            catch (System.Exception e)
            {
              NxtControl.Services.LoggingService.ErrorFormatted(@"In Event Callback for event:'{0}' Type:'{1}' CAT:'{2}' came exception:{3}
stack Trace:
{4}","REQ1_Fired", this.GetType().Name, this.CATName, e.Message, e.StackTrace);
            }
          }
        break; 
        case 2:
          if (REQ2_Fired != null)
          {
            try
            {
              REQ2_Fired(this, new HMI.Main.Symbols.ReqPanelLift.REQ2EventArgs(channelId, cookie, eventIndex));
            }
            catch (System.Exception e)
            {
              NxtControl.Services.LoggingService.ErrorFormatted(@"In Event Callback for event:'{0}' Type:'{1}' CAT:'{2}' came exception:{3}
stack Trace:
{4}","REQ2_Fired", this.GetType().Name, this.CATName, e.Message, e.StackTrace);
            }
          }
        break; 

      }
    }
    public bool FireEvent_CHG(System.Boolean ButtonFor0, System.Boolean ButtonFor1, System.Boolean ButtonFor2)
    {
      return ((IHMIAccessorOutput)this).FireEvent(0, new object[] {ButtonFor0, ButtonFor1, ButtonFor2});
    }
    public bool FireEvent_CHG(HMI.Main.Symbols.ReqPanelLift.CHGEventArgs ea)
    {
      object[] _values_ = new object[3];
      if (ea.ButtonFor0.HasValue) _values_[0] = ea.ButtonFor0.Value;
      if (ea.ButtonFor1.HasValue) _values_[1] = ea.ButtonFor1.Value;
      if (ea.ButtonFor2.HasValue) _values_[2] = ea.ButtonFor2.Value;
      return ((IHMIAccessorOutput)this).FireEvent(0, _values_);
    }
    public bool FireEvent_CHG(System.Boolean ButtonFor0, bool ignore_ButtonFor0, System.Boolean ButtonFor1, bool ignore_ButtonFor1, System.Boolean ButtonFor2, bool ignore_ButtonFor2)
    {
      object[] _values_ = new object[3];
      if (!ignore_ButtonFor0) _values_[0] = ButtonFor0;
      if (!ignore_ButtonFor1) _values_[1] = ButtonFor1;
      if (!ignore_ButtonFor2) _values_[2] = ButtonFor2;
      return ((IHMIAccessorOutput)this).FireEvent(0, _values_);
    }

  }
}
#endregion #ReqPanelLift_HMI;

#endregion Definitions;
