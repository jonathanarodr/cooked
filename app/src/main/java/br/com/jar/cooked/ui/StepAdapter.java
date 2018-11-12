package br.com.jar.cooked.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.jar.cooked.R;
import br.com.jar.cooked.model.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private final StepAdapterOnClickHandler mClickHandler;
    private List<Step> mSteps;

    public StepAdapter(StepAdapterOnClickHandler clickHandler, List<Step> Steps) {
        mClickHandler = clickHandler;
        mSteps = Steps;
    }

    class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mStepName;

        StepHolder(@NonNull View itemView) {
            super(itemView);
            mStepName = itemView.findViewById(R.id.tv_step_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(mSteps.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_step, viewGroup, false);
        return new StepHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder StepHolder, int i) {
        Step Step = mSteps.get(i);
        StepHolder.mStepName.setText(Step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return (mSteps == null) ? 0 : mSteps.size();
    }

}
