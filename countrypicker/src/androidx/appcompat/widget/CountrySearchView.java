package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.hendraanggrian.appcompat.countrypicker.R;

/**
 * A hacked {@link SearchView} that mimics search bar in Android Settings app API 28.
 * This class needs to be in this particular package since it's accessing internal components.
 *
 * <p> Note: {@link android.widget.SearchView} is not hackable since it uses internal R class.</p>
 */
@SuppressWarnings("FieldCanBeLocal")
public final class CountrySearchView extends SearchView {

    private final View mSearchEditFrame;
    private final View mSearchPlate;
    private final View mSubmitArea;

    public CountrySearchView(Context context) {
        this(context, null);
    }

    public CountrySearchView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.searchViewStyle);
    }

    public CountrySearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSearchEditFrame = findViewById(androidx.appcompat.R.id.search_edit_frame);
        mSearchPlate = findViewById(androidx.appcompat.R.id.search_plate);
        mSubmitArea = findViewById(androidx.appcompat.R.id.submit_area);

        // Set up icons and backgrounds.
        final Drawable transparent =
            new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent));
        ViewCompat.setBackground(mSearchPlate, transparent);
        ViewCompat.setBackground(mSubmitArea, transparent);

        mSearchSrcTextView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        // First, remove horizontal paddings to get full width.
        final MarginLayoutParams frameLp = (MarginLayoutParams) mSearchEditFrame.getLayoutParams();
        frameLp.leftMargin = 0;
        frameLp.rightMargin = 0;

        final MarginLayoutParams textLp = (MarginLayoutParams) mSearchSrcTextView.getLayoutParams();
        textLp.leftMargin = getResources()
            .getDimensionPixelSize(R.dimen.countrypicker_searchbar_gap);

        // Buttons are wider in Google Search app.
        mCloseButton.setScaleType(ImageView.ScaleType.CENTER);
        mCloseButton.getLayoutParams().width = getDimenAttr(context, android.R.attr.actionBarSize);
    }

    @NonNull
    public EditText getInput() {
        return mSearchSrcTextView;
    }

    private static int getColorAttr(Context context, int attrId) {
        final TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrId, typedValue, true);
        return typedValue.data;
    }

    @SuppressWarnings("SameParameterValue")
    private static int getDimenAttr(Context context, int attrId) {
        final TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrId, typedValue, true);
        final int[] textSizeAttr = new int[]{attrId};
        TypedArray a = context.obtainStyledAttributes(typedValue.data, textSizeAttr);
        final int value = a.getDimensionPixelSize(0, 0);
        a.recycle();
        return value;
    }
}