package simugen.core.interfaces;

public interface SimOutputHook
{
	public void hookTo(SimInputHook downStreamHook);
	public SimInputHook getHookedTo();
}
