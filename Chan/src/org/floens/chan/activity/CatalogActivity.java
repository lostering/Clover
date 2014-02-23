package org.floens.chan.activity;

import org.floens.chan.R;
import org.floens.chan.fragment.ThreadFragment;
import org.floens.chan.model.Loadable;
import org.floens.chan.model.Pin;
import org.floens.chan.model.Post;
import org.floens.chan.model.Loadable.Mode;
import org.floens.chan.net.ChanUrls;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

public class CatalogActivity extends BaseActivity {
	private final Loadable loadable = new Loadable();
	
	private ThreadFragment threadFragment;
	
    @Override
	public void onOPClicked(Post post) {
		
	}

	@Override
	public void openPin(Pin post) {
		
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        loadable.mode = Mode.CATALOG;
        
        threadFragment = ThreadFragment.newInstance(this);
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, threadFragment);
        ft.commitAllowingStateLoss();*/
        
        Bundle bundle = getIntent().getExtras();
        
        // Favor savedInstanceState bundle over intent bundle:
        // the intent bundle may be old, for example when the user
        // switches the loadable through the drawer.
        if (savedInstanceState != null) {
            loadable.readFromBundle(this, savedInstanceState);
        } else if (bundle != null) {
            loadable.readFromBundle(this, bundle);
        } else {
            finish();
        }
        
        startLoading(loadable);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        loadable.writeToBundle(this, outState);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_reload:
        	threadFragment.reload();
            
            return true;
        case R.id.action_open_browser:
            showUrlOpenPicker(ChanUrls.getCatalogUrlDesktop(loadable.board));
            
            return true;
        case android.R.id.home:
            finish();
            
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    
    private void startLoading(Loadable loadable) {
        threadFragment.startLoading(loadable);
        
        setShareUrl(ChanUrls.getCatalogUrlDesktop(loadable.board));
        
        if (TextUtils.isEmpty(loadable.title)) {
            loadable.title = "Catalog /" + loadable.board + "/";
        }
        
        getActionBar().setTitle(loadable.title);
    }
}




