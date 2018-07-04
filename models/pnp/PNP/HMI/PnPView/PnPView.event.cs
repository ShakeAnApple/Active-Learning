/*
 * Created by nxtSTUDIO.
 * User: XPMUser
 * Date: 2/8/2012
 * Time: 11:04 AM
 * 
 * To change this template use Tools | Options | Coding | Edit Standard Headers.
 */
using System;
using NxtControl.GuiFramework;
using NxtControl.Services;

#region Definitions;
#region #PnPView_HMI;

namespace NxtStudio.Symbols.PnPView
{

  public class REQEventArgs : System.EventArgs
  {
    IHMIAccessorService accessorService;
    int channelId;
    int cookie; 
    int eventIndex;

    public REQEventArgs(int channelId, int cookie, int eventIndex)
    {
      this.accessorService = (IHMIAccessorService)ServiceProvider.GetService(typeof(IHMIAccessorService));
      this.channelId = channelId;
      this.cookie = cookie;
      this.eventIndex = eventIndex;
    }
    public bool Get_BASE_X(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,0, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_BASE_Y(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,1, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_CYL1_X(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,2, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_CYL1_Y(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,3, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_CYL2_X(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,4, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_CYL2_Y(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,5, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_VCYL_X(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,6, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_VCYL_Y(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,7, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_WP1_X(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,8, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_WP1_Y(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,9, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_WP2_X(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,10, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_WP2_Y(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,11, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_WP3_X(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,12, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_WP3_Y(ref System.UInt16 value)
    {
      if (accessorService == null)
        return false;
      System.Int64 b = 0;
      bool ret = accessorService.GetInt64Value(channelId, cookie, eventIndex, true,13, ref b);
      if (ret) value = (System.UInt16) b;
      return ret;
    }
    public bool Get_VACUUM_ON(ref System.Boolean value)
    {
      if (accessorService == null)
        return false;
      return accessorService.GetBoolValue(channelId, cookie, eventIndex, true,14, ref value);
    }

  }

}

namespace NxtStudio.Symbols.PnPView
{
  partial class HMI
  {

    private event EventHandler<NxtStudio.Symbols.PnPView.REQEventArgs> REQ_Fired;

    protected override void OnEndInit()
    {
      if (REQ_Fired != null)
        AttachEventInput(0);

    }

    protected override void FireEventCallback(int channelId, int cookie, int eventIndex)
    {
      switch(eventIndex)
      {
        default:
          break;
        case 0:
          if (REQ_Fired != null)
            REQ_Fired(this, new NxtStudio.Symbols.PnPView.REQEventArgs(channelId, cookie, eventIndex));
        break; 

      }
    }
    public bool FireEvent_CNF(System.Boolean ADD1, System.Boolean ADD2, System.Boolean ADD3)
    {
      return ((IHMIAccessorOutput)this).FireEvent(0, new object[] {ADD1, ADD2, ADD3});
    }
    public bool FireEvent_CNF(System.Boolean ADD1, bool ignore_ADD1, System.Boolean ADD2, bool ignore_ADD2, System.Boolean ADD3, bool ignore_ADD3)
    {
      object[] _values_ = new object[3];
      if (!ignore_ADD1) _values_[0] = ADD1;
      if (!ignore_ADD2) _values_[1] = ADD2;
      if (!ignore_ADD3) _values_[2] = ADD3;
      return ((IHMIAccessorOutput)this).FireEvent(0, _values_);
    }

  }
}

namespace NxtStudio.Faceplates.PnPView
{
  partial class Faceplate
  {

    private event EventHandler<NxtStudio.Symbols.PnPView.REQEventArgs> REQ_Fired;

    protected override void OnEndInit()
    {
      if (REQ_Fired != null)
        AttachEventInput(0);

    }

    protected override void FireEventCallback(int channelId, int cookie, int eventIndex)
    {
      switch(eventIndex)
      {
        default:
          break;
        case 0:
          if (REQ_Fired != null)
            REQ_Fired(this, new NxtStudio.Symbols.PnPView.REQEventArgs(channelId, cookie, eventIndex));
        break; 

      }
    }
    public bool FireEvent_CNF(System.Boolean ADD1, System.Boolean ADD2, System.Boolean ADD3)
    {
      return ((IHMIAccessorOutput)this).FireEvent(0, new object[] {ADD1, ADD2, ADD3});
    }
    public bool FireEvent_CNF(System.Boolean ADD1, bool ignore_ADD1, System.Boolean ADD2, bool ignore_ADD2, System.Boolean ADD3, bool ignore_ADD3)
    {
      object[] _values_ = new object[3];
      if (!ignore_ADD1) _values_[0] = ADD1;
      if (!ignore_ADD2) _values_[1] = ADD2;
      if (!ignore_ADD3) _values_[2] = ADD3;
      return ((IHMIAccessorOutput)this).FireEvent(0, _values_);
    }

  }
}
#endregion #PnPView_HMI;

#endregion Definitions;
